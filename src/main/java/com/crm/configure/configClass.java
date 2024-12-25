package com.crm.configure;

import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configClass {
    @Bean
    public ModelMapper getModel(){
        return new ModelMapper();
    }
}
