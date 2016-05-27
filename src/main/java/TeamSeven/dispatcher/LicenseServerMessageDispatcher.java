package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.handler.licenseServer.ReqLicenseServerUserLimitationHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/5/27.
 */
public class LicenseServerMessageDispatcher extends MessageDispatcher {

    public LicenseServerMessageDispatcher(Object applier) {
        super(applier);
    }

    @Override
    public void dispatch(BaseMessage message, WebSocket connFrom) {
        switch (message.getType()) {
            case REQ_LICENSE_SERVER_USER_LIMITATION:
                handler = new ReqLicenseServerUserLimitationHandler(message, connFrom, applier);
                break;
            default:
                System.out.println("Message type not found.");
                break;
        }
    }
}
