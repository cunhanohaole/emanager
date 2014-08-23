package br.com.tscpontual.test;

import java.io.File;
import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTestCase {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void logBefore() {
        File buildDir = new File("target");
        if (!buildDir.exists())
            buildDir.mkdir();
        URL resource = getClass().getClassLoader().getResource("log4j.xml");
        System.out.println("configuring log4j with <" + resource.getFile() + ">");
        DOMConfigurator.configure(resource);
        log.info("start test");
    }

    @After
    public void logAfter() {
        log.info("end test");
    }
	
}
