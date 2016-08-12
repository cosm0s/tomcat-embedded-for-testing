package com.cosm0s.embedded;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.Logger;

public class TomcatEmbedded {

    private static final Logger LOG = Logger.getLogger(TomcatEmbedded.class.getName());
    private static String tmpDir = System.getProperty("java.io.tmpdir");

    private static TomcatEmbedded instance = null;
    private static Tomcat tomcat = null;

    private static String applicationID;

    private TomcatEmbedded() {}

    public static TomcatEmbedded getInstance(String id) throws LifecycleException {
        if(instance == null) {
            synchronized (TomcatEmbedded.class) {
                if(instance == null) {
                    applicationID = id;
                    tomcat = new Tomcat();
                    startTomcat();
                    instance = new TomcatEmbedded();
                }
            }
        }
        return instance;
    }

    public static void stopTomcat() throws LifecycleException {
        System.out.println("Stop the server...");
        if (tomcat.getServer() != null && tomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
                tomcat.stop();
            }
            tomcat.destroy();
        }
    }

    public static void startTomcat() throws LifecycleException {
        LOG.info("Tomcat's base directory : " + tmpDir + "\nSetup new tomcat server");
        setup();
        LOG.info("Add web app");
        tomcat.addWebapp(tomcat.getHost(), "/" + applicationID, new File(tmpDir, applicationID).getAbsolutePath());
        LOG.info("Start tomcat server");
        tomcat.start();
    }

    private static void setup() {
        tomcat.setPort(8080);
        tomcat.setBaseDir(tmpDir);
        tomcat.getHost().setAppBase(tmpDir);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.addUser("admin", "admin");
        tomcat.addUser("test", "test");
        tomcat.addRole("admin", "admin");
        tomcat.addRole("admin", "user");
        tomcat.addRole("test", "user");
    }

    public static String getTmpDir() {
        return tmpDir;
    }
}
