package TeamSeven.entry;

import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocketImpl;

/**
 * Created by joshoy on 16/4/17.
 */
public class ServerConsoleEntry {

    public static void main(String[] args) {

        // WebSocketImpl.DEBUG = true;
        ChatRoomServerConsole serverConsole = new ChatRoomServerConsole("demoServerConfig");

    }

}
