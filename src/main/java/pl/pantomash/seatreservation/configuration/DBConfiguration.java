package pl.pantomash.seatreservation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devDatabaseConnection() {
        return "DB CONNCECTION FOR DEV";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection() {
        return "PROD CONNECTION";
    }
}
