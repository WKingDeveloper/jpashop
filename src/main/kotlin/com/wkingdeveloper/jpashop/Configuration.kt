package com.wkingdeveloper.jpashop

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun hibernate5Module(): Hibernate5JakartaModule {
        val hibernate5JakartaModule = Hibernate5JakartaModule()
        //강제 지연 로딩 설정
//        hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true)
        return hibernate5JakartaModule
    }
}
