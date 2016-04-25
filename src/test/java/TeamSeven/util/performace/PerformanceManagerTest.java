package TeamSeven.util.performace;

import org.junit.Before;
import org.junit.Test;
import sun.print.PeekGraphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by tina on 16/4/25.
 */
public class PerformanceManagerTest
{

    PerformanceManagerImpl pm = null;
    @Before
    public void setUp() throws Exception {
        pm = new PerformanceManagerImpl();
        pm.initClientPm( "oneClient" );

    }

    @Test
    public void clientAddMessage() throws Exception
    {
        pm.clientAddMessage( "testing.." );
        pm.clientAddMessage( "testing.." );
        assertEquals( pm.mc.getSendMessageCount(), 2 );
        String path = pm.getMessageFilePath();

        File file = new File( path );
        BufferedReader reader = null;

        try {
            System.out.println( path );
            reader = new BufferedReader( new FileReader( file ) );
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        pm.doCompass();
    }

    @Test
    public void addReceivedMessage() throws Exception
    {
        pm.addReceivedMessage();
        assertEquals( pm.mc.getReceivedMessageCount(), 1 );
    }

    @Test
    public void addIgnoredMessage() throws Exception
    {
        pm.addIgnoredMessage();
        assertEquals( pm.mc.getIgnoredMessageCount(), 1 );

    }

}