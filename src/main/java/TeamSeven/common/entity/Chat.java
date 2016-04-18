package TeamSeven.common.entity;

/**
 * Created by joshoy on 16/4/18.
 */
public class Chat {

    private String content;   // 对话消息内容
    private Account account;  // 发送者的account

    public Chat(String content, Account account) {
        this.content = content;
        this.account = account;
    }

    public String getContent() {
        return this.content;
    }

    public Account getAccount() {
        return this.account;
    }

}
