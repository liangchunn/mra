package application;

import dbadapter.GroupFacade;

import java.util.Timer;
import java.util.TimerTask;

public class AutoDeleteTimer {
    static Timer timer = new Timer();
    public static void main(String[] args) {
        // Schedules a timer at a fixed interval of 30 seconds to run the autoDeleteGroups function
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int status = GroupFacade.getInstance().autoDeleteGroups();
                System.out.println("Auto Delete finished with status " + status);
            }
        }, 0, 30 * 1000);
    }
}
