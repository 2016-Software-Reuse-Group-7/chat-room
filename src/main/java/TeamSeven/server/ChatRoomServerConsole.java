package TeamSeven.server;

import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;

import java.net.UnknownHostException;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomServerConsole {

    protected final int port = 8077;
    protected ConsoleServerSideMessageDispatcher dispatcher;
    protected ChatRoomServerSocket ss;

    public ChatRoomServerConsole() {

        // 创建一个Console Dispatcher
        // 将this作为applier参数传入后, 我们在自定义的handler里就可以直接调用本类提供的方法
        dispatcher = new ConsoleServerSideMessageDispatcher(this);

        try {
            ss = new ChatRoomServerSocketImpl(port, dispatcher);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /* API for handlers */

    /**
     * 在console上打印文字
     * @param line: String line
     */
    public void consoleAppendLine(String prefix, String line) {
        System.out.println("[" + prefix + "] " + line);
    }

}
