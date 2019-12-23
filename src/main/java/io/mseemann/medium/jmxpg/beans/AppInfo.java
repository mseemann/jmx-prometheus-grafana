package io.mseemann.medium.jmxpg.beans;

public class AppInfo implements AppInfoMBean {

    private String status;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
