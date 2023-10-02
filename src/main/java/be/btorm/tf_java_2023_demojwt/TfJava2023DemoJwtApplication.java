package be.btorm.tf_java_2023_demojwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties
public class TfJava2023DemoJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TfJava2023DemoJwtApplication.class, args);
    }

}
