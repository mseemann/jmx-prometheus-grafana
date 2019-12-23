package io.mseemann.medium.jmxpg;

import io.mseemann.medium.jmxpg.beans.Requests;
import lombok.SneakyThrows;

import java.util.Random;

public class RequestSimulation implements Runnable {

    private final Requests requests;

    public RequestSimulation(Requests requests) {
        this.requests = requests;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            Thread.sleep(new Random().nextInt(50));
            requests.newRequest();
        }
    }
}
