package com.wkingdeveloper.jpashop

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun hibernate5Module(): Hibernate5Module {
        return Hibernate5Module()
    }
}
