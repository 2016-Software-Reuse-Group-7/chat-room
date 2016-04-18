package TeamSeven.common.entity;

/**
 * Created by joshoy on 16/4/18.
 */
public class Boardcast {

    String content;   // 服务端广播内容
    String sign;      // 服务端签名, 用于验证

    public Boardcast(String content, String sign) {
        this.content = content;
        this.sign = sign;
    }

    public boolean verify() {
        // TODO: 验证服务端签名
        return true;
    }

    public String getContent() {
        return this.content;
    }

}
