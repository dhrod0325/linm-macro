package lm.macro;

import lm.macro.spring.config.LmWebConfiguration;
import org.apache.commons.lang3.RandomUtils;
import org.opencv.core.Core;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@ComponentScan("lm.macro")
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Main extends SpringBootServletInitializer {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.setProperty("java.awt.headless", "false");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int v = RandomUtils.nextInt(0, 3);
            System.out.println(v);
        }

//        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Main.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(LmWebConfiguration.class);
        ctx.setServletContext(servletContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }
}