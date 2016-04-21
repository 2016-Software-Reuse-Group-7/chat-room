package TeamSeven.server.zip;

import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NFDFlightDataTimerTask extends TimerTask {
 
    @Override
    public void run() {
        try {
            ZipFile zf=new ZipFile();
            zf.ZipFile("f:/uu", "f:/zippath.zip");
            System.out.println("aaa!!!");

        } catch (Exception e) {

        }
    }
}