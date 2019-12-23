package io.mseemann.medium.jmxpg;

import io.mseemann.medium.jmxpg.beans.Requests;
import lombok.SneakyThrows;

import java.util.Random;

public class RequestSimulation implements Runnable {

    private final Requests requests;
    private final int randomBound;

    public RequestSimulation(Requests requests, int randomBound) {
        this.requests = requests;
        this.randomBound = randomBound;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            Thread.sleep(new Random().nextInt(randomBound));
            requests.newRequest();
        }
    }
}
