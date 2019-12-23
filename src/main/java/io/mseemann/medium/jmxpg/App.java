package io.mseemann.medium.jmxpg;

import io.mseemann.medium.jmxpg.beans.AppInfo;
import io.mseemann.medium.jmxpg.beans.Requests;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;

public class App {

    static final String NAMESPACE = "jmxpg.mbeans";
    static final String REQUESTS_O_NAME = NAMESPACE + ":type=Requests";

    public static void main(String... args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        AppInfo appInfo = new AppInfo();
        appInfo.setStatus("starting");

        Hashtable<String, String> keyValues = new Hashtable<>();
        keyValues.put("type", "AppInfo");
        keyValues.put("Version", App.class.getPackage().getImplementationVersion());
        mbs.registerMBean(appInfo, new ObjectName("jmxpg.mbeans", keyValues));


        Requests requests = new Requests();
        mbs.registerMBean(requests, new ObjectName(REQUESTS_O_NAME));

        new Thread(new RequestSimulation(requests)).start();
        new Thread(new AppStateSimulation(appInfo)).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> appInfo.setStatus("exited")));
        Thread.sleep(Integer.MAX_VALUE);
    }

}
