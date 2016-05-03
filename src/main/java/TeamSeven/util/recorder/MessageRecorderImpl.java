package TeamSeven.util.recorder;

import TeamSeven.util.config.ConfigManagerImpl;
import log.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tina on 16/5/3.
 */
public class MessageRecorderImpl implements MessageRecorder {

    private String messageFilePath;
    private ConfigManagerImpl configManager = null;

    private String messageFileName;

    public MessageRecorderImpl() {

        this.configManager = new ConfigManagerImpl( "demoServerConfig" );
        this.messageFilePath = this.configManager.getString( "log.outPath" );

        newMessageFile();

    }

    public MessageRecorderImpl( String clientName ) {

        this.configManager = new ConfigManagerImpl( "demoClientConfig" );
        this.messageFilePath = this.configManager.getString( "log.outPath" ) + clientName + "/";

        newMessageFile();

    }

    public void newMessageFile() {

        Date dt = new Date();
        messageFileName = new SimpleDateFormat( "yyyyMMddHHmmss" ).format( dt );

        Log.createFile( messageFilePath + messageFileName + ".txt" );
    }

    // 记录消息
    public void writeMessage( String content ) throws Exception
    {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
        String time = df.format( dt );
        String path = messageFilePath + messageFileName + ".txt";

        Log.writeFile( path, time + ": " + content );
    }

    public String getMessageFilePath()
    {
        return messageFilePath + messageFileName + ".txt";
    }
}
