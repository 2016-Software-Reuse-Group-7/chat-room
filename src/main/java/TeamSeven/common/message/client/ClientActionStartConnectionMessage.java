package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by joshoy on 16/4/21.
 */
public class ClientActionStartConnectionMessage extends BaseMessage {

    protected URI serverUri;

    public ClientActionStartConnectionMessage() {

    }

    public ClientActionStartConnectionMessage(URI serverUri) {
        this.serverUri = serverUri;
    }

    public ClientActionStartConnectionMessage(String serverIp, int serverPort) throws URISyntaxException {
        this.serverUri = new URI("ws://" + serverIp + ":" + serverPort);
    }

    public URI getServerUri() {
        return serverUri;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_ACT_START_CONNECTION;
    }
}
