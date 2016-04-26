package TeamSeven.server.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ZipFile {
	  public static void ZipFile(String filepath ,String zippath) {
	        try {
	            File file = new File(filepath);
	            File zipFile = new File(zippath);
	            InputStream input = null;
	            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
	            if(file.isDirectory()){
	                File[] files = file.listFiles();
	                for(int i = 0; i < files.length; ++i){
	                    input = new FileInputStream(files[i]);
	                    zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
	                    int temp = 0;
	                    while((temp = input.read()) != -1){
	                        zipOut.write(temp);
	                    }
	                    input.close();
	                }
	            }
	            zipOut.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
