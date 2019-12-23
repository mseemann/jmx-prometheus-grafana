package io.mseemann.medium.jmxpg;

import io.mseemann.medium.jmxpg.beans.AppInfo;
import io.mseemann.medium.jmxpg.beans.Requests;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;

public class App {

    static final String NAMESPACE = "jmxpg.mbeans";
    static final String GET_REQUESTS_O_NAME = NAMESPACE + ":type=Requests,verb=GET";
    static final String POST_REQUESTS_O_NAME = NAMESPACE + ":type=Requests,verb=POST";

    public static void main(String... args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        AppInfo appInfo = new AppInfo();
        appInfo.setStatus("starting");

        Hashtable<String, String> keyValues = new Hashtable<>();
        keyValues.put("type", "AppInfo");
        keyValues.put("Version", App.class.getPackage().getImplementationVersion());
        mbs.registerMBean(appInfo, new ObjectName("jmxpg.mbeans", keyValues));


        Requests getRequests = new Requests();
        mbs.registerMBean(getRequests, new ObjectName(GET_REQUESTS_O_NAME));

        Requests postRequests = new Requests();
        mbs.registerMBean(postRequests, new ObjectName(POST_REQUESTS_O_NAME));

        new Thread(new RequestSimulation(getRequests, 50)).start();
        new Thread(new RequestSimulation(postRequests, 150)).start();
        new Thread(new AppStateSimulation(appInfo)).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> appInfo.setStatus("exited")));

        Thread.sleep(Integer.MAX_VALUE);
    }

}
