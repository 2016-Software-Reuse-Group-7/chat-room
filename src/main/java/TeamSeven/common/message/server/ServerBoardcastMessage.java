package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * Created by joshoy on 16/4/18.
 */
public class ServerBoardcastMessage extends BaseMessage {

    public String getContent() {
        return content;
    }

    protected String content;

    public ServerBoardcastMessage(String content) {
        this.content = content;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_BOARDCAST;
    }
}
