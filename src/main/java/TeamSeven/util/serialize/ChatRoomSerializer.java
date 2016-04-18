package TeamSeven.util.serialize;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by joshoy on 16/4/17.
 */
public interface ChatRoomSerializer {
    byte[] serializeObject(Serializable obj) throws IOException;
    String serializeObjectAndStringify(Serializable obj) throws IOException;
    Object deserializeObject(byte[] buffer) throws IOException, ClassNotFoundException;
    Object deserializeStringifyObject(String buffer) throws IOException, ClassNotFoundException;
}
