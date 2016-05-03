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

        pm = new PerformanceManagerImpl( "oneClient" );
    }

    @Test
    public void clientAddMessage() throws Exception {

        pm.clientAddMessage();
        pm.clientAddMessage();
        assertEquals( pm.mc.getSendMessageCount(), 2 );

    }

    @Test
    public void addReceivedMessage() throws Exception {

        pm.addReceivedMessage();
        assertEquals( pm.mc.getReceivedMessageCount(), 1 );
    }

    @Test
    public void addIgnoredMessage() throws Exception {

        pm.addIgnoredMessage();
        assertEquals( pm.mc.getIgnoredMessageCount(), 1 );

    }

}