package io.mseemann.medium.jmxpg;

import io.mseemann.medium.jmxpg.beans.AppInfo;
import lombok.SneakyThrows;

import java.time.Duration;

public class AppStateSimulation implements Runnable {

    private final AppInfo appInfo;

    public AppStateSimulation(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(Duration.ofSeconds(10).toMillis());
            String newState = appInfo.getStatus().equals("running") ? "failed" : "running";
            appInfo.setStatus(newState);
        }
    }
}
