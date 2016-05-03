package TeamSeven.util.recorder;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by tina on 16/5/3.
 */
public class MessageRecorderImplTest {

    @Test
    public void writeMessage() throws Exception {


        MessageRecorder clientMR = new MessageRecorderImpl( "oneClient" );

        clientMR.writeMessage( "testing..." );
        clientMR.writeMessage( "testing..." );

        String path = clientMR.getMessageFilePath();

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
    }

}