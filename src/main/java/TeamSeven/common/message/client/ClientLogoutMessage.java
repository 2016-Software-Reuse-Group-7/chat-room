package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * Created by joshoy on 16/4/19.
 */
public class ClientLogoutMessage extends BaseMessage {

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_LOGOUT;
    }

}
