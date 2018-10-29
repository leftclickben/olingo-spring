package net.readify.olingo.demo;

import net.readify.olingo.demo.web.ODataServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

@Configuration
public class WebAppInitializer implements ServletContextInitializer {
    public void onStartup(ServletContext container) {
        container
            .addServlet("odata", new ODataServlet())
            .addMapping("/odata/*");
    }
}
