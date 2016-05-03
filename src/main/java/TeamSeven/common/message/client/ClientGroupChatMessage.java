package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * 为群组聊天新建一类 消息类型
 * Created by zhao on 2016/5/2.
 */
public class ClientGroupChatMessage extends BaseMessage {

    /**
     * 群组号码
     */
    protected Long groupId;

    /**
     * message中的内容
     */
    protected String content;

    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_GROUP_CHAT;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
