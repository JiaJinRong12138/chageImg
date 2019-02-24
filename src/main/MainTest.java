package main;

import utils.DownUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainTest {
    public static void main(String[] args) {
        downloadeTimer();

    }

    private static void downloadeTimer(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                DownUtils.downWallPaper();
            }
        };
//        设置时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                8,
                00,
                00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(timerTask, date);
    }
}
