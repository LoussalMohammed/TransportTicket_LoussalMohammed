package org.app.Models.Scheldures;

import java.util.Timer;
import java.util.TimerTask;

import static org.app.Models.Checkers.ContractChecker.contractMonitor;

public class ContractMonitorScheduler {

    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                contractMonitor();
            }
        }, 0, 1200000); // Starts immediately, repeats every 20 minutes
    }
}

