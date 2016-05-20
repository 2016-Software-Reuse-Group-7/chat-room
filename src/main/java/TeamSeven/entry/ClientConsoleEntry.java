package TeamSeven.entry;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.client.ClientActionStartConnectionMessage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by joshoy on 16/4/17.
 */
public class ClientConsoleEntry {
    public static void main(String[] args) {

        ChatRoomClientConsole clientConsole = new ChatRoomClientConsole("demoClientConfig");
        clientConsole.selfDispatch(new ClientActionStartConnectionMessage(), null);
    }

}
