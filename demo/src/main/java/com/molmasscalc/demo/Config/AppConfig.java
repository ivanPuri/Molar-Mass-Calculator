package com.molmasscalc.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String chemicalFormula() {
        return "H2O"; // Provide the actual value
    }
}


