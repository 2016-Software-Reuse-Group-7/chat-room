package TeamSeven.util.config;

/**
 * Created by joshoy on 16/4/17.
 */
public interface ChatRoomConfigReader {

    String getConfigItemStringByName(String configItemName);
    String getConfigItemIntByName(String configItemName);

}
