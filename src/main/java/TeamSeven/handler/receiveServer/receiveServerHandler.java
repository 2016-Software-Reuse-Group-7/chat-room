package TeamSeven.handler.receiveServer;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.receiverServer.ReqLicenseServerUserLimitationMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.licenseServer.LicenseServer;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/5/27.
 */
public class receiveServerHandler extends BaseHandler {

    public receiveServerHandler(WebSocket conn) {
        super(conn);
    }

    protected ReqLicenseServerUserLimitationMessage message;
    protected LicenseServer server;

    public receiveServerHandler(BaseMessage msg, WebSocket conn, Object applier) {
        super(conn);
        message = (ReqLicenseServerUserLimitationMessage) msg;
        server = (LicenseServer) applier;
    }

    @Override
    public void onHandle() {
        String userId = message.getAccount().getUserId();
    }
}
