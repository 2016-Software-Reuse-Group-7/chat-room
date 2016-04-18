package TeamSeven.common.exception;

/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomUndefinedMessageTypeException extends ChatRoomBaseException {

    public ChatRoomUndefinedMessageTypeException() {
        super("消息类型未定义.");
    }

}
