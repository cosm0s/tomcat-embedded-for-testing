package com.cosm0s.embedded.test;

import com.cosm0s.embedded.TomcatEmbedded;
import org.apache.catalina.LifecycleException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TomcatEmbeddedTest {

    @BeforeClass
    public static void init() throws IOException, LifecycleException {
        File war = new File(TomcatEmbedded.getTmpDir(), "showcase-6.0.war");
        if(!war.exists()) {
            FileUtils
                    .copyInputStreamToFile(
                            new URL("http://repository.primefaces.org/org/primefaces/showcase/6.0/showcase-6.0.war").openConnection().getInputStream(), war
                    );
        }
        TomcatEmbedded.getInstance("showcase-6.0");
    }

    @AfterClass
    public static void end() throws LifecycleException {
        TomcatEmbedded.stopTomcat();
    }

    @Test
    public void test_tomcat_embedded() {
        while(true){}
    }
}
