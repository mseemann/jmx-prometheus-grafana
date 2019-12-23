package io.mseemann.medium.jmxpg.beans;

public class Requests implements RequestsMBean {

    private long requestCount = 0;

    @Override
    public Long getRequestCount() {
        return requestCount;
    }

    public void newRequest() {
        requestCount++;

    }
}
