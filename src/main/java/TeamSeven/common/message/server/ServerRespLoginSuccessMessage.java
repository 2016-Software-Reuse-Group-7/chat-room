package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerRespLoginSuccessMessage extends BaseMessage {
    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_RESP_LOGIN_SUCCESS;
    }
}
