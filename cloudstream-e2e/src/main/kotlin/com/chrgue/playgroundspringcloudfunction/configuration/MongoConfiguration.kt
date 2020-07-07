package com.chrgue.playgroundspringcloudfunction.configuration

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class MongoConfiguration {

    @Bean
    fun mongoBuilderCustomizer() = MongoClientSettingsBuilderCustomizer { builder->
        builder.applyToClusterSettings {
            it.serverSelectionTimeout(10, TimeUnit.SECONDS)
        }
    }
}