package TeamSeven.util.serialize;

import org.nustaq.serialization.FSTConfiguration;

import java.io.Serializable;
import java.util.Base64;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomSerializerImpl implements ChatRoomSerializer {

    public static FSTConfiguration conf = null;

    public ChatRoomSerializerImpl() {
        if (null == this.conf) {
            this.conf = FSTConfiguration.createDefaultConfiguration();
        }
    }

    public byte[] serializeObject(Serializable obj) {
        byte[] bArray = conf.asByteArray(obj);
        byte[] ret = Base64.getEncoder().encode(bArray);
        return ret;
    }

    public String serializeObjectAndStringify(Serializable obj) {
        return new String(this.serializeObject(obj));
    }

    public Object deserializeObject(byte[] buffer) {
        byte[] data = Base64.getDecoder().decode(buffer);
        return conf.asObject(data);
    }

    public Object deserializeStringifyObject(String buffer) {
        return this.deserializeObject(buffer.getBytes());
    }
}
