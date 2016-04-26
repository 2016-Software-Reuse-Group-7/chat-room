package TeamSeven.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by joshoy on 16/4/18.
 */
public class Chat implements Serializable {

    private String content;   // 聊天消息内容
    private Date chatTime;    // 聊天时间

    public Chat(String content) {
        this.content = content;
        this.chatTime = new Date();
    }

    public String getContent() {
        return this.content;
    }

    public Date getChatTime() {
        return this.chatTime;
    }

}
