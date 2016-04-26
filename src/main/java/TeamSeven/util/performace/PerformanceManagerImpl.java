package TeamSeven.util.performace;

import TeamSeven.common.performance.MessageCount;
import log.Log;
import TeamSeven.util.performace.zip.*;

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
    private String zipFilePath;
    private String messageFileName;
    // 性能指数文件名: filePath + "/messages/" + initTime
    // 消息记录文件名: filePath + "/log/" + initTime

    // 服务端开启时
    public void initServerPm() throws IOException
    {
        log = new Log();
        mc = new MessageCount( true );

        log.setPMDir( filePath + "/log/" );

        filePath = "logFiles/serverLog";
        zipFilePath = "logFiles/zipFiles/server";


        startLog();
    }

    // 客户端开启时
    public void initClientPm( String name ) throws IOException
    {
        log = new Log();
        mc = new MessageCount( false );

        filePath = "logFiles/clientLog/" + name;
        zipFilePath = "logFiles/zipFiles/" + name;
        log.setPMDir( filePath + "/log/" );

        newMessageFile();
        startLog();
    }

    // 客户端发送一条消息
    public void clientAddMessage( String content ) throws Exception
    {
        mc.addSendMessageCount( 1 );
        log.setParam( "发送消息数量", mc.getSendMessageCount() );
        writeMessage( content );

    }

    // 服务端接收一条消息 or 客户端一条消息被接收
    public void addReceivedMessage()
    {
        mc.addReceivedMessageCount( 1 );
        log.setParam( "接收消息数量", mc.getReceivedMessageCount() );
    }

    // 服务端忽略一条消息 or 客户端一条消息被接收
    public void addIgnoredMessage()
    {
        mc.addIgnoredMessageCount( 1 );
        log.setParam( "接收消息数量", mc.getIgnoredMessageCount() );
    }

    // 开始生成文件
    public void startLog() throws IOException {
        log.setDelay( 0 );
        log.run();
        startCompass();
    }

    // 停止定时生成文件,并将未被压缩的文件压缩
    public void endLog() throws IOException {
        ZipManager.doCompass( zipFilePath );
        log.stop();
    }



    // 定时调用doCompass
    public void startCompass()
    {
        ZipManager timer = new ZipManager();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                doCompass();

                Log.resetCompress();
                newMessageFile();
            }
        };
        timer.setTimerTask( task );
    }

    public void doCompass()
    {
        ZipManager.doCompass( zipFilePath );
    }



    public void newMessageFile()
    {
        Date dt = new Date();
        messageFileName = new SimpleDateFormat( "yyyyMMddHHmmss" ).format( dt );
        log.createFile( filePath + "/messages/" + messageFileName + ".txt" );
    }

    // 记录消息
    public void writeMessage( String content ) throws Exception
    {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
        String time = df.format( dt );
        String path = filePath + "/messages/" + messageFileName + ".txt";

        Log.writeFile( path, time + ": " + content );
    }

    public String getMessageFilePath()
    {
        return filePath + "/messages/" + messageFileName + ".txt";
    }
}
