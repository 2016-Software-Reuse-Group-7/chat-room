package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.util.Date;

/**
 * Created by joshoy on 16/4/18.
 */
public class ServerBoardcastMessage extends BaseMessage {

    protected Date sendTime;
    protected String content;

    public ServerBoardcastMessage(String content) {
        this.content = content;
        this.sendTime = new Date();
    }

    public String getContent() {
        return this.content;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_BOARDCAST;
    }
}
