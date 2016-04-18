package TeamSeven.handler;

import TeamSeven.common.exception.ChatRoomUndefinedMessageTypeException;
import TeamSeven.common.message.ChatRoomBaseMessage;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class ChatRoomBaseHandler {
    public abstract void handleMessage(ChatRoomBaseMessage msg) throws ChatRoomUndefinedMessageTypeException;
}
