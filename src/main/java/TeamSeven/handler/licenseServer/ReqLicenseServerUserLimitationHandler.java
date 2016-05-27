package TeamSeven.handler.licenseServer;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.receiverServer.ReqLicenseServerUserLimitationMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.licenseServer.LicenseServer;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/5/27.
 */
public class ReqLicenseServerUserLimitationHandler extends BaseHandler {

    public ReqLicenseServerUserLimitationHandler(WebSocket conn) {
        super(conn);
    }

    protected ReqLicenseServerUserLimitationMessage message;
    protected LicenseServer server;

    public ReqLicenseServerUserLimitationHandler(BaseMessage msg, WebSocket conn, Object applier) {
        super(conn);
        message = (ReqLicenseServerUserLimitationMessage) msg;
        server = (LicenseServer) applier;
    }

    @Override
    public void onHandle() {
        String userId = message.getAccount().getUserId();
        server.printLine("Get request for userId: " + userId);
    }
}
