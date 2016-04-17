package TeamSeven.util.serialize;

import java.io.Serializable;

/**
 * Created by joshoy on 16/4/17.
 */
public interface ChatRoomSerializer {
    byte[] serializeObject(Serializable obj);
    String serializeObjectAndStringify(Serializable obj);
    Object deserializeObject(byte[] buffer);
    Object deserializeStringifyObject(String buffer);
}
