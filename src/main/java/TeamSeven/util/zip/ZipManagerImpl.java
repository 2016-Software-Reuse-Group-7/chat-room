package TeamSeven.util.zip;

import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.recorder.MessageRecorder;
import TeamSeven.util.recorder.MessageRecorderImpl;
import TeamSeven.util.sizeLimitation.SizeLimitationManager;
import TeamSeven.util.sizeLimitation.SizeLimitationManagerImpl;
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
    private SizeLimitationManager sizeLimitationManager;

    public MessageRecorder getMessageRecorder() {
        return messageRecorder;
    }
    public SizeLimitationManager getSizeLimitationManager() {
        return sizeLimitationManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }

    private String zipFilePath;
    private String weeklyZipFilePath;
    private String serverLogFilePath;

    public String getZipFilePath() {
        return zipFilePath;
    }

    public String getWeeklyZipFilePath() {
        return weeklyZipFilePath;
    }

    // 时间间隔
    private long periodDay = 24 * 60 * 60 * 1000;
    private Timer timer = null;
    private Date zipTime = null;

    private long periodWeek = periodDay * 7;
    private Timer weeklyTimer = null;
    private Date weeklyZipTime = null;

    public Date getZipTime() {
        return zipTime;
    }

    public void setZipTime( int hour ) {

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, hour);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        this.zipTime = c1.getTime();
        if (this.zipTime.before(new Date())) {
            this.zipTime = this.timerAddDay(zipTime, 1);
        }
    }

    public long getPeriodWeek() {
        return periodWeek;
    }

    public void setPeriodWeek(long periodWeek) {
        this.periodWeek = periodWeek;
    }

    public Date getWeeklyZipTime() {
        return weeklyZipTime;
    }

    public void setWeeklyZipTime( int hour, int day ) {

        Calendar c2 = Calendar.getInstance();
        int day_of_week = c2.get(Calendar.DAY_OF_WEEK) - 1;
        if (day > 7 || day < 1 ) {
            day = 1;
        }

        if (day_of_week == 0)
            day_of_week = 7;
        c2.add(Calendar.DATE, -day_of_week + day);
        c2.set(Calendar.HOUR_OF_DAY, hour);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        this.weeklyZipTime = c2.getTime();
        if (this.weeklyZipTime.before(new Date())) {
            this.weeklyZipTime = this.timerAddDay(weeklyZipTime, 7);
        }
    }

    public long getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(long periodDay) {
        this.periodDay = periodDay;
    }



    public ZipManagerImpl() throws Exception {

        this.configManager = new ConfigManagerImpl("demoServerConfig");
        this.zipFilePath = this.configManager.getString("log.zip.zipPath");
        this.weeklyZipFilePath = this.configManager.getString("log.zip.rezipPath");
        this.serverLogFilePath = "logFiles/server/serverlog";
        Log.recordFileName.add( serverLogFilePath );

        this.messageRecorder = new MessageRecorderImpl();
        this.sizeLimitationManager = new SizeLimitationManagerImpl(this.configManager.getInt("log.messages.singleFileSize"), this.configManager.getInt("log.messages.totalFileSize"));

        initTimer();
        startZip();
    }

    public ZipManagerImpl(String clientName) throws Exception {

        this.configManager = new ConfigManagerImpl("demoClientConfig");
        this.zipFilePath = this.configManager.getString("log.zip.zipPath") + clientName + "/";
        this.weeklyZipFilePath = this.configManager.getString("log.zip.rezipPath") + clientName + "/";
        this.serverLogFilePath = "logFiles/client/clientlog";
        Log.recordFileName.add( serverLogFilePath );

        this.messageRecorder = new MessageRecorderImpl(clientName);
        this.sizeLimitationManager = new SizeLimitationManagerImpl(this.configManager.getInt("log.messages.singleFileSize"), this.configManager.getInt("log.messages.totalFileSize"));

        initTimer();
        startZip();
    }

    private void initTimer() {

        this.periodDay = this.configManager.getLong("log.zip.zipPeriod");
        this.periodWeek = this.configManager.getLong("log.zip.rezipPeriod");

        int zipHour = this.configManager.getInt("log.zip.zipHour");
        setZipTime(zipHour);
        timer = new Timer();

        zipHour = this.configManager.getInt("log.zip.rezipHour");
        int rezipDay = configManager.getInt("log.zip.rezipDay");
        setWeeklyZipTime(zipHour, rezipDay);
        weeklyTimer = new Timer();
    }

    private Date timerAddDay(Date date, int num) {

        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    private void startZip() throws Exception {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                doDailyCompass();

            }
        };
        timer.schedule(task, zipTime, periodDay);

        checkWeeklyZip();
        TimerTask weeklyTask = new TimerTask() {
            @Override
            public void run() {
                List<File> zipFileList = null;

                try {

                    zipFileList = getWeeklyZipFiles(zipTime);
                    if (zipFileList != null && zipFileList.size() != 0) {
                        doWeeklyCompass(zipFileList);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        weeklyTimer.schedule(weeklyTask, weeklyZipTime, periodWeek);
    }

    public void endZip() throws IOException {

        doDailyCompass();
        timer.cancel();
        weeklyTimer.cancel();
    }

    public void doDailyCompass() {

        try {
            Date dt = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = df.format(dt);



            compress(zipFilePath + time + ".zip");

            messageRecorder.newMessageFile();
            Log.recordFileName.add( serverLogFilePath );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void messageRecord(String content) throws Exception {

        String fileName = messageRecorder.getMessageFilePath();
        if ( ! sizeLimitationManager.checkSingleFileSize( fileName, content ) ) {
            messageRecorder.newMessageFile();
        }
        messageRecorder.writeMessage(content);

        if ( ! sizeLimitationManager.checkFolderSize( messageRecorder.getMessageFolderName() ) ) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime( date );
            cal.add( Calendar.DATE, -7 );
            date = cal.getTime();
            sizeLimitationManager.cleanFolder( messageRecorder.getMessageFolderName(), date );
            System.out.println( "自动清理一周前的message log file." );
            if ( ! sizeLimitationManager.checkFolderSize( messageRecorder.getMessageFolderName() ) ) {
                System.out.println( "文件夹大小仍超过限制,请手动清理!" );
            }
        }
    }

    public void doWeeklyCompass(List<File> zipFileList) throws Exception {

        Date dt = new Date();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(dt);

        String destFileName = weeklyZipFilePath + time + ".zip";
        String destTempDir = weeklyZipFilePath + time;

        for (File file : zipFileList) {
            decompress(file, destTempDir);
            sizeLimitationManager.delete(file.getAbsolutePath());
        }

        compress(destFileName, destTempDir);
        sizeLimitationManager.deleteDirectory(destTempDir);

    }

    private boolean checkWeeklyZip() throws Exception {

        Calendar c = Calendar.getInstance();
        c.setTime(weeklyZipTime);
        c.add(Calendar.DATE, -7);
        List<File> zipFileList = getWeeklyZipFiles(c.getTime());

        if (zipFileList == null || zipFileList.size() == 0) {
            return true;
        } else {
            doWeeklyCompass(zipFileList);
            return false;
        }
    }

    public List<File> getWeeklyZipFiles(Date weeklyZipTime) throws ParseException {

        List<File> list = null;

        File desFile = new File(zipFilePath);
        if (desFile.exists()) {

            list = new LinkedList<File>();
            File[] files = desFile.listFiles();
            for (File file : files) {

                if (file.isFile() && file.getName().substring(file.getName().lastIndexOf(".")).equals(".zip")) {

                    String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                    if (!name.equals("")) {

                        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(name);
                        if (date.before(weeklyZipTime)) {
                            list.add(file);
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
    public boolean compress(String destFileName) {

        try {
            Log.compress(destFileName);
            Log.resetCompress();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean compress(String zipFileName, String inputFile) throws Exception {

        File file = new File(zipFileName);
        fileProber(file);

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        compress(out, new File(inputFile), "");
        out.close();
        return true;
    }

    public void compress(ZipOutputStream out, File file, String base) throws Exception {

        if (file.isDirectory()) {

            File[] fl = file.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                compress(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(file);
            int b;
            System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    // 解压
    public void decompress(String srcPath) throws Exception {

        File srcFile = new File(srcPath);

        decompress(srcFile);
    }

    public void decompress(File srcFile) throws Exception {

        String basePath = srcFile.getParent();
        decompress(srcFile, basePath);
    }

    public void decompress(File srcFile, File destFile) throws Exception {

        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());

        ZipInputStream zis = new ZipInputStream(cis);

        decompress(destFile, zis);

        zis.close();

    }

    public void decompress(File srcFile, String destPath) throws Exception {

        decompress(srcFile, new File(destPath));
    }

    public void decompress(String srcPath, String destPath) throws Exception {

        File srcFile = new File(srcPath);
        decompress(srcFile, destPath);
    }

    public void decompress(File destFile, ZipInputStream zis) throws Exception {

        ZipEntry entry = null;
        while ((entry = zis.getNextEntry()) != null) {

            // 文件
            String dir = destFile.getPath() + File.separator + entry.getName();

            File dirFile = new File(dir);

            // 文件检查
            fileProber(dirFile);

            if (entry.isDirectory()) {
                dirFile.mkdirs();
            } else {
                decompressFile(dirFile, zis);
            }

            zis.closeEntry();
        }
    }

    public void fileProber(File dirFile) {

        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {

            // 递归寻找上级目录
            fileProber(parentFile);

            parentFile.mkdir();
        }

    }

    public void decompressFile(File destFile, ZipInputStream zis) throws Exception {

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, count);
        }

        bos.close();
    }

}
