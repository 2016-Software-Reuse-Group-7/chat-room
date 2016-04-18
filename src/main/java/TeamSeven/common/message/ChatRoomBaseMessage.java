package TeamSeven.common.message;

import TeamSeven.common.enumerate.TransMessageTypeEnum;

import java.io.Serializable;

/**
 * Created by joshoy on 16/4/17.
 */
public abstract class ChatRoomBaseMessage implements Serializable {
    public abstract TransMessageTypeEnum getType();
}
