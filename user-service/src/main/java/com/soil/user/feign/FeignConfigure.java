package com.soil.user.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigure {

    @Bean
    public Request.Options options() {
        return new Request.Options(1000, 2000);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
