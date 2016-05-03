package TeamSeven.util.zip;

import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.recorder.MessageRecorder;
import TeamSeven.util.recorder.MessageRecorderImpl;
import log.Log;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

/**
 * Created by tina on 16/5/2.
 */
public class ZipManagerImpl implements ZipManager {

    private ConfigManager configManager;
    private MessageRecorder messageRecorder;
    private String zipFilePath;
    private String weeklyZipFilePath;

    public String getZipFilePath() {
        return zipFilePath;
    }

    public String getWeeklyZipFilePath() {
        return weeklyZipFilePath;
    }

    // 时间间隔
    private static final long periodDay = 24 * 60 * 60 * 1000;
    Timer timer = null;
    Date zipTime = null;

    private static final long periodWeek = periodDay * 7;
    Timer weeklyTimer = null;
    Date weeklyZipTime = null;

    public Date getWeeklyZipTime() {
        return weeklyZipTime;
    }

    public Date getZipTime() {
        return zipTime;
    }

    public ZipManagerImpl() throws Exception {

        this.configManager = new ConfigManagerImpl( "demoServerConfig" );
        this.zipFilePath = this.configManager.getString( "log.zipPath" );
        this.weeklyZipFilePath = this.configManager.getString( "log.weeklyZipPath" );

        this.messageRecorder = new MessageRecorderImpl();

        initTimer();
        startZip();
    }

    public ZipManagerImpl( String clientName ) throws Exception {

        this.configManager = new ConfigManagerImpl( "demoClientConfig" );
        this.zipFilePath = this.configManager.getString( "log.zipPath" ) + clientName + "/";
        this.weeklyZipFilePath = this.configManager.getString( "log.weeklyZipPath" ) + clientName + "/";

        this.messageRecorder = new MessageRecorderImpl( clientName );

        initTimer();
        startZip();
    }

    private void initTimer() {

        Calendar c1 = Calendar.getInstance();

        // 每天2:00压缩
        c1.set( Calendar.HOUR_OF_DAY, 2 );
        c1.set( Calendar.MINUTE, 0 );
        c1.set( Calendar.SECOND, 0 );
        zipTime = c1.getTime();
        if (zipTime.before( new Date() ) )
        {
            zipTime = this.timerAddDay(zipTime, 1);
        }

        timer = new Timer();

        Calendar c2 = Calendar.getInstance();

        // 每周周一3:00压缩
        int day_of_week = c2.get( Calendar.DAY_OF_WEEK ) - 1;
        if ( day_of_week == 0 )
            day_of_week = 7;
        c2.add( Calendar.DATE, -day_of_week + 8 );
        c2.set( Calendar.HOUR_OF_DAY, 3 );
        c2.set( Calendar.MINUTE, 0 );
        c2.set( Calendar.SECOND, 0 );
        weeklyZipTime = c2.getTime();

        weeklyTimer = new Timer();
    }

    private Date timerAddDay(Date date, int num) {

        Calendar startDT = Calendar.getInstance();
        startDT.setTime( date );
        startDT.add( Calendar.DAY_OF_MONTH, num );
        return startDT.getTime();
    }

    private void startZip() throws Exception {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                doDailyCompass();

            }
        };
        timer.schedule( task, zipTime, periodDay );

        checkWeeklyZip();
        TimerTask weeklyTask = new TimerTask() {
            @Override
            public void run() {
                List<File> zipFileList = null;

                try {

                    zipFileList = getWeeklyZipFiles( zipTime );
                    if ( zipFileList != null && zipFileList.size() != 0 ) {
                        doWeeklyCompass( zipFileList );
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        weeklyTimer.schedule( weeklyTask, weeklyZipTime, periodWeek );
    }

    public void endZip() throws IOException {

        doDailyCompass();
        timer.cancel();
        weeklyTimer.cancel();
    }

    public void doDailyCompass() {

        try
        {
            Date dt = new Date();
            DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
            String time = df.format( dt );
            compress( zipFilePath + time + ".zip" );

            messageRecorder.newMessageFile();

        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void messageRecord( String content ) throws Exception {

        messageRecorder.writeMessage( content );
    }

    public void doWeeklyCompass( List<File> zipFileList ) throws Exception {

        Date dt = new Date();
        String time = new SimpleDateFormat( "yyyyMMddHHmmss" ).format( dt );

        String destFileName = weeklyZipFilePath + time + ".zip";
        String destTempDir = weeklyZipFilePath + time;

        for ( File file : zipFileList ) {
            decompress( file, destTempDir );
            delete( file.getAbsolutePath() );
        }

        compress( destFileName, destTempDir );
        deleteDirectory( destTempDir );

    }

    private boolean checkWeeklyZip() throws Exception {

        Calendar c = Calendar.getInstance();
        c.setTime( weeklyZipTime );
        c.add( Calendar.DATE, -7 );
        List<File> zipFileList = getWeeklyZipFiles( c.getTime() );

        if (  zipFileList == null || zipFileList.size() == 0 ) {
            return true;
        } else {
            doWeeklyCompass( zipFileList );
            return false;
        }
    }

    public List<File> getWeeklyZipFiles( Date weeklyZipTime ) throws ParseException {

        List<File> list = null;

        File desFile = new File( zipFilePath );
        if ( desFile.exists() ) {

            list = new LinkedList<File>();
            File[] files = desFile.listFiles();
            for ( File file : files ) {

                if ( file.isFile() && file.getName().substring( file.getName().lastIndexOf( "." ) ).equals( ".zip"  ) ) {

                    String name = file.getName().substring( 0, file.getName().lastIndexOf( "." ) );
                    if ( !name.equals("") ) {

                        Date date = new SimpleDateFormat( "yyyyMMddHHmmss" ).parse( name );
                        if ( date.before( weeklyZipTime ) ) {
                            list.add( file );
                        }
                    }
                }
            }

        } else {
            System.out.println("文件不存在!");
        }

        return list;
    }



    private static final int BUFFER = 1024;

    // 压缩
    public boolean compress( String destFileName ) {

        try {
            Log.compress( destFileName );
            Log.resetCompress();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean compress(String zipFileName, String inputFile) throws Exception {

        File file = new File( zipFileName );
        fileProber( file );

        ZipOutputStream out = new ZipOutputStream( new FileOutputStream( zipFileName ) );
        compress( out, new File( inputFile ), "" ) ;
        out.close();
        return true;
    }

    public void compress( ZipOutputStream out, File file, String base ) throws Exception {

        if ( file.isDirectory() ) {

            File[] fl = file.listFiles();
            out.putNextEntry( new ZipEntry( base + "/" ) );
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                compress( out, fl[i], base + fl[i].getName() );
            }
        }else {
            out.putNextEntry( new ZipEntry( base ) );
            FileInputStream in = new FileInputStream( file );
            int b;
            System.out.println( base );
            while ( ( b = in.read() ) != -1 ) {
                out.write( b );
            }
            in.close();
        }
    }

    // 解压
    public void decompress( String srcPath ) throws Exception {

        File srcFile = new File( srcPath );

        decompress( srcFile );
    }

    public void decompress( File srcFile ) throws Exception {

        String basePath = srcFile.getParent();
        decompress( srcFile, basePath );
    }

    public void decompress( File srcFile, File destFile ) throws Exception {

        CheckedInputStream cis = new CheckedInputStream( new FileInputStream( srcFile ), new CRC32() );

        ZipInputStream zis = new ZipInputStream( cis );

        decompress( destFile, zis );

        zis.close();

    }

    public void decompress( File srcFile, String destPath ) throws Exception {

        decompress( srcFile, new File( destPath ) );
    }

    public void decompress( String srcPath, String destPath ) throws Exception {

        File srcFile = new File( srcPath );
        decompress( srcFile, destPath );
    }

    public void decompress( File destFile, ZipInputStream zis ) throws Exception {

        ZipEntry entry = null;
        while ( ( entry = zis.getNextEntry() ) != null ) {

            // 文件
            String dir = destFile.getPath() + File.separator + entry.getName();

            File dirFile = new File( dir );

            // 文件检查
            fileProber( dirFile );

            if ( entry.isDirectory() ) {
                dirFile.mkdirs();
            } else {
                decompressFile( dirFile, zis );
            }

            zis.closeEntry();
        }
    }

    public void fileProber( File dirFile) {

        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {

            // 递归寻找上级目录
            fileProber(parentFile);

            parentFile.mkdir();
        }

    }

    public void decompressFile( File destFile, ZipInputStream zis ) throws Exception {

        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( destFile ) );

        int count;
        byte data[] = new byte[BUFFER];
        while ( ( count = zis.read( data, 0, BUFFER ) ) != -1 ) {
            bos.write( data, 0, count );
        }

        bos.close();
    }

    // 删除文件
    public boolean delete( String fileName ) {

        File file = new File( fileName );
        if ( !file.exists() ) {
            System.out.println("删除文件失败："+fileName+"文件不存在");
            return false;
        } else {
            if ( file.isFile() ) {

                return deleteFile( fileName );
            } else {
                return deleteDirectory( fileName );
            }
        }
    }

    public boolean deleteFile( String fileName ) {

        File file = new File( fileName );
        if( file.isFile() && file.exists() ) {

            file.delete();
            System.out.println( "删除单个文件"+fileName+"成功！" );
            return true;
        } else {

            System.out.println( "删除单个文件"+fileName+"失败！" );
            return false;
        }
    }

    public boolean deleteDirectory( String dir ) {

        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if( !dir.endsWith( File.separator ) ) {

            dir = dir + File.separator;
        }
        File dirFile = new File( dir );
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if( !dirFile.exists() || !dirFile.isDirectory() ) {

            System.out.println( "删除目录失败" + dir + "目录不存在！" );
            return false;
        }

        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for( int i = 0; i < files.length; i ++ ) {

            //删除子文件
            if( files[i].isFile() ) {

                flag = deleteFile( files[i].getAbsolutePath() );
                if( !flag ) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDirectory( files[i].getAbsolutePath() );
                if( !flag ) {
                    break;
                }
            }
        }

        if( !flag ) {

            System.out.println( "删除目录失败" );
            return false;
        }

        //删除当前目录
        if( dirFile.delete() ) {
            System.out.println( "删除目录"+dir+"成功！" );
            return true;
        } else {
            System.out.println( "删除目录"+dir+"失败！" );
            return false;
        }
    }


}
