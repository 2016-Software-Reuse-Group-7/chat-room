package TeamSeven.util.serialize;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomSerializerImplTest {

    @Test
    public void testSerializer() throws Exception {
        ChatRoomSerializer serializer = new ChatRoomSerializerImpl();
        String str = "测试序列化";
        System.out.println("序列化前: " + str);

        String encoded = serializer.serializeObjectAndStringify(str);
        System.out.println("序列化后: " + encoded);

        String decoded = (String)serializer.deserializeStringifyObject(encoded);
        System.out.println("反序列化后: " + decoded);
    }

}