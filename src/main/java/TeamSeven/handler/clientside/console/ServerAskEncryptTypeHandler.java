package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.client.ClientRespEncryptTypeMessage;
import TeamSeven.common.message.server.ServerAskEncryptTypeMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import org.java_websocket.WebSocket;

import javax.crypto.SecretKey;

/**
 * Created by joshoy on 16/4/22.
 */
public class ServerAskEncryptTypeHandler extends BaseHandler {

    ServerAskEncryptTypeMessage message;
    ChatRoomClientConsole clientConsole;

    public ServerAskEncryptTypeHandler(ServerAskEncryptTypeMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        // 首先我们要读取一下公钥
        clientConsole.setEncryptType(EncryptTypeEnum.RSA);
        clientConsole.setServerPublicKey(message.getServerPublicKey());
        // 处理服务器请求加密类型的要求,
        // 需要从配置文件中读取客户端设置的加密类型
        EncryptTypeEnum eType = this.clientConsole.readEncryptionTypeFromConfig();
        // 判断是对称还是非对称加密
        SymmetricCoder sc = null;
        AsymmertricCoder ac = null;
        if (eType.isSymmetricEncryption()) {
            try {
                sc = (SymmetricCoder) eType.getCoderClass().newInstance();
                SecretKey secretKey = sc.spanKey();
                clientConsole.sendMessageWithEncryption(new ClientRespEncryptTypeMessage(eType, secretKey));
                // 发送完之后就可以改密钥了
                clientConsole.setEncryptType(eType);
                clientConsole.setConnectionEncryptKey(secretKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                // ac = (AsymmertricCoder) eType.getCoderClass().newInstance();
                clientConsole.sendMessageWithEncryption(new ClientRespEncryptTypeMessage(eType, message.getServerPublicKey()));

                clientConsole.setEncryptType(eType);
                clientConsole.setConnectionEncryptKey(message.getServerPublicKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
