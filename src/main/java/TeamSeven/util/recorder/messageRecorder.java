package TeamSeven.util.recorder;

/**
 * Created by tina on 16/5/3.
 */
public interface MessageRecorder {

    void newMessageFile();

    // 记录消息
    void writeMessage( String content ) throws Exception;

    String getMessageFilePath();
}
