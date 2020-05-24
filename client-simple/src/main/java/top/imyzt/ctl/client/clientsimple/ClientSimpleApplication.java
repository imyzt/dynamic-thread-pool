package top.imyzt.ctl.client.clientsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author imyzt
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "top.imyzt.ctl"
})
@EnableScheduling
public class ClientSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientSimpleApplication.class, args);
    }
}
