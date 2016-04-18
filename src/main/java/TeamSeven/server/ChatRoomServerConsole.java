package TeamSeven.server;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.account.AccountManager;
import TeamSeven.server.account.AccountManagerImpl;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.encrypt.AES.AESCoder;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.RSA.RSACoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.WebSocket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomServerConsole {

    protected final int port = 8077;
    /**
     * 设置选项: 是否加密
     */
    protected final boolean encryptMessage = true;
    /*
     * 设置选项: 加密方式
     * */
    protected final EncryptTypeEnum encryptType = EncryptTypeEnum.RSA;
    /*
    *  加密Tool
    * */
    protected SymmetricCoder symmetricCoder = null;
    protected AsymmertricCoder asymmertricCoder = null;

    /*
    * 账号管理
    * */
    protected AccountManager accountManager = null;
    /*
    * Session管理
    * */
    protected SessionManager sessionManager = null;

    protected ConsoleServerSideMessageDispatcher dispatcher;
    protected ChatRoomServerSocket ss;

    public ChatRoomServerConsole() {
        // 创建一个Console Dispatcher
        // 将this作为applier参数传入后, 我们在自定义的handler里就可以直接调用本类提供的方法
        this.dispatcher = new ConsoleServerSideMessageDispatcher(this);
        this.accountManager = new AccountManagerImpl();
        this.sessionManager = new SessionManagerImpl();

        // TODO: 根据配置信息选择加密方式
        try {
            this.symmetricCoder = new AESCoder();
            this.asymmertricCoder = new RSACoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        // 创建Server WebSocket
        try {
            ss = new ChatRoomServerSocketImpl(port, this.dispatcher, this.sessionManager);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /* *
     *  +------------------+
     *  | API for handlers |
     *  +------------------+
     * */

    /**
     * API: 在console上打印文字
     * @param line: String line
     */
    public void consoleAppendLine(String prefix, String line) {
        System.out.println("[" + prefix + "] " + line);
    }

    /**
     * API: 串加密, 使用的是加密模块
     * @param buffer 需加密的串
     * @param key 加密用的密钥
     * @return
     */
    public String encrypt(String buffer, Key key) {
        // 加密方式为对称加密
        if (this.encryptType.isSymmetricEncryption()) {
            SecretKey secKey = (SecretKey) key;
            try {
                return new String(this.symmetricCoder.encrypt(buffer, secKey));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        // 加密方式为非对称加密
        else {
            PublicKey pubKey = (PublicKey) key;
            this.asymmertricCoder.setPublicKey(pubKey);
            try {
                return new String(this.asymmertricCoder.encryptWithPublicKey(buffer.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
