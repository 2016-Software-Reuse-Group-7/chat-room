package TeamSeven.common.exception;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomEncryptTypeErrorException extends ChatRoomBaseException {
    public ChatRoomEncryptTypeErrorException() {
        super("加密方式有误.");
    }
}
