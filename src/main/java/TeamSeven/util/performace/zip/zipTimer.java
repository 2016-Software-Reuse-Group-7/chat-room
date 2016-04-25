package TeamSeven.util.performace.zip;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class zipTimer
{

	// 时间间隔
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	Timer timer = null;
	Date zipDate = null;

	public zipTimer() {
		Calendar calendar = Calendar.getInstance();

		// 每天2:00压缩
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 10);
		calendar.set(Calendar.SECOND, 10);

		zipDate = calendar.getTime();

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (zipDate.before(new Date())) {
			zipDate = this.addDay(zipDate, 1);
		}

		timer = new Timer();
	}

	public void setTimerTask( TimerTask task )
	{
		timer.schedule( task, zipDate, PERIOD_DAY );
	}

	// 添加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}



	