package com.cosm0s.embedded.test;

import com.cosm0s.embedded.TomcatEmbedded;
import org.apache.catalina.LifecycleException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

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
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/showcase-6.0/");
        WebElement element = driver.findElement(By.id("LOGOTEXTSIDE")).findElement(By.className("logoBlueText"));
        assertTrue("Showcase is down", element.getText().equals("SHOWCASE"));
        driver.quit();
    }

}
