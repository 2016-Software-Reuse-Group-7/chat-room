package TeamSeven.server.Zip;

import TeamSeven.server.Zip.NFDFlightDataTimerTask;

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

public class Time { 
                private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
		public Time() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 10);
		calendar.set(Calendar.SECOND, 10);

		Date date=calendar.getTime(); 
		if (date.before(new Date())) {
		date = this.addDay(date, 1);
		}

		Timer timer = new Timer();

		NFDFlightDataTimerTask task = new NFDFlightDataTimerTask();
		timer.schedule(task,date,PERIOD_DAY);
		}

		public Date addDay(Date date, int num) {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, num);
			return startDT.getTime();
		}
		
		 public static void main(String arg[]) {
		        new Time();
		    }

}



	
