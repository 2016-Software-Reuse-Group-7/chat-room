package TeamSeven.util.zip;

import TeamSeven.util.performace.PerformanceManager;
import TeamSeven.util.performace.PerformanceManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tina on 16/5/3.
 */
public class ZipManagerImplTest {

    @Test
    public void doDailyCompass() throws Exception {

        PerformanceManager performanceManager = new PerformanceManagerImpl();
        ZipManager zipManager = new ZipManagerImpl();

        zipManager.messageRecord( "testing..." );
        zipManager.messageRecord( "testing..." );
        performanceManager.addReceivedMessage();
        performanceManager.addIgnoredMessage();

        zipManager.doDailyCompass();
    }

    @Test
    public void doWeeklyCompass() throws Exception {

        PerformanceManager performanceManager = new PerformanceManagerImpl();
        ZipManagerImpl zipManager = new ZipManagerImpl();

        zipManager.messageRecord( "testing..." );
        zipManager.messageRecord( "testing..." );
        performanceManager.addReceivedMessage();
        performanceManager.addIgnoredMessage();

        zipManager.doDailyCompass();

        List<File> zipFileList = zipManager.getWeeklyZipFiles( new Date() );

        if ( zipFileList != null ) {
            zipManager.doWeeklyCompass( zipFileList );

        }
    }

    @Test
    public void testSet() throws Exception {

        ZipManagerImpl zipManager = new ZipManagerImpl();
        System.out.println( zipManager.getZipTime() );
        zipManager.setZipTime( 4 );
        System.out.println( zipManager.getZipTime() );

        System.out.println( zipManager.getWeeklyZipTime() );
        zipManager.setWeeklyZipTime( 1, 5 );
        System.out.println( zipManager.getWeeklyZipTime() );


    }
}