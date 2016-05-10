package TeamSeven.util.sizeLimitation;

import TeamSeven.util.zip.ZipManager;
import TeamSeven.util.zip.ZipManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tina on 16/5/9.
 */
public class SizeLimitationManagerImplTest {

    @Test
    public void testFolderSize1() throws Exception {

        SizeLimitationManagerImpl sizeLimitationManager = new SizeLimitationManagerImpl();
        long size = sizeLimitationManager.folderSize( new File( "logFiles/server/messages" ) );
        System.out.println( "file size: " + size );
        System.out.println( sizeLimitationManager.checkFolderSize( "logFiles/server/messages", 100 ) );
    }

    @Test
    public void testFolderSize2() throws Exception {

        SizeLimitationManagerImpl sizeLimitationManager = new SizeLimitationManagerImpl();
        long size = sizeLimitationManager.folderSize( new File( "logFiles/server/messages" ) );
        System.out.println( "file size: " + size );
        System.out.println( sizeLimitationManager.checkFolderSize( "logFiles/server/messages", 100 ) );
    }

    @Test
    public void testSingleFileSize() throws Exception {

        SizeLimitationManagerImpl sizeLimitationManager = new SizeLimitationManagerImpl();
        ZipManagerImpl zipManager = new ZipManagerImpl();

        String test = "testing...";
        String fileName = zipManager.getMessageRecorder().getMessageFilePath();

        while ( sizeLimitationManager.checkSingleFileSize( fileName, test, 100 ) ) {
            zipManager.messageRecord( test );
            File file = new File( fileName );
            System.out.println( file.length() );
        }

    }

    @Test
    public void testCleanFolder() throws Exception {

        SizeLimitationManagerImpl sizeLimitationManager = new SizeLimitationManagerImpl();
        ZipManagerImpl zipManager = new ZipManagerImpl();

        String test = "testing...";
        String fileName = zipManager.getMessageRecorder().getMessageFilePath();
        String folderName = zipManager.getMessageRecorder().getMessageFolderName();

        while ( sizeLimitationManager.checkFolderSize( folderName, 500*1024 ) ) {
            zipManager.messageRecord( test );
            File folder = new File( folderName );
            System.out.println( sizeLimitationManager.folderSize( folder) );
        }

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.add( Calendar.HOUR, -2 );
        date = cal.getTime();
        sizeLimitationManager.cleanFolder( zipManager.getMessageRecorder().getMessageFolderName(), date );
    }
}