package TeamSeven.entry;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.client.ClientActionStartConnectionMessage;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by joshoy on 16/4/17.
 */
public class ClientConsoleEntry {

    public static void main(String[] args) {

        ChatRoomClientConsole clientConsole = new ChatRoomClientConsole();
        try {
            clientConsole.selfDispatch(new ClientActionStartConnectionMessage(
                    "localhost", 8077
            ), null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // clientConsole.selfDispatch();

        // clientConsole.getClientSocket().connect();
    }

}
