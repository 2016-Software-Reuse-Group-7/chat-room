package TeamSeven.common.message.server;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.util.Date;

/**
 * Created by zhao on 2016/5/31.
 */
public class ServerDelayChatMessage extends BaseMessage {

    private String content;

    public ServerDelayChatMessage(String content){
        this.content = content;
    }

    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_DELAY_CHAT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
