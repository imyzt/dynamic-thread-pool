package top.imyzt.ctl.client.clientsimple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author imyzt
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "top.imyzt.ctl"
})
@EnableScheduling
public class ClientSimpleApplication {

    private static final Logger log = LoggerFactory.getLogger(ClientSimpleApplication.class);

    private final ThreadPoolTaskExecutor printThread;

    public ClientSimpleApplication(ThreadPoolTaskExecutor printThread) {
        this.printThread = printThread;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientSimpleApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            int finalI = i;
            printThread.execute(() -> {
                log.info("i={}", finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(3000);
        }
        return null;
    }
}
