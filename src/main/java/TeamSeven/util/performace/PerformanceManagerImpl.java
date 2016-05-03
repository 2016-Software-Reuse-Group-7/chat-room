package TeamSeven.util.performace;

import TeamSeven.common.performance.MessageCount;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.recorder.MessageRecorder;
import TeamSeven.util.recorder.MessageRecorderImpl;
import log.Log;
import TeamSeven.util.zip.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by joshoy on 16/4/17.
 */
public class PerformanceManagerImpl implements PerformanceManager
{
    public Log log;
    public MessageCount mc;

    private String filePath;

    private ConfigManager configManager;

    public PerformanceManagerImpl() throws IOException {

        log = new Log();
        mc = new MessageCount( true );

        this.configManager = new ConfigManagerImpl( "demoServerConfig" );
        this.filePath = this.configManager.getString( "log.performanceLogPath" );
        log.setPMDir(filePath);

        startLog();
    }

    public PerformanceManagerImpl( String name ) throws IOException {

        log = new Log();
        mc = new MessageCount( false );

        this.configManager = new ConfigManagerImpl( "demoClientConfig" );
        this.filePath = this.configManager.getString( "log.performanceLogPath" ) + name + "/";
        log.setPMDir(filePath);

        startLog();
    }

    // 客户端发送一条消息
    public void clientAddMessage() throws Exception {

        mc.addSendMessageCount( 1 );
        log.setParam( "发送消息数量", mc.getSendMessageCount() );
    }

    // 服务端接收一条消息 or 客户端一条消息被接收
    public void addReceivedMessage() {

        mc.addReceivedMessageCount( 1 );
        log.setParam( "接收消息数量", mc.getReceivedMessageCount() );
    }

    // 服务端忽略一条消息 or 客户端一条消息被接收
    public void addIgnoredMessage() {

        mc.addIgnoredMessageCount( 1 );
        log.setParam( "接收消息数量", mc.getIgnoredMessageCount() );
    }

    // 开始生成文件
    public void startLog() throws IOException {

        log.setDelay( 0 );
        log.run();
    }

    // 停止定时生成文件,并将未被压缩的文件压缩
    public void endLog() throws IOException {

        log.stop();
    }

}
