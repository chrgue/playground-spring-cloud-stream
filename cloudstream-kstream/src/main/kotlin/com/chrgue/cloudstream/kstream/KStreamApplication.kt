package com.chrgue.cloudstream.kstream

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Function

@SpringBootApplication
class KStreamApplication {

    @Bean
    fun processor() = Function<KStream<String, String>, KStream<String, String>> {
        it.map { k, v ->
            KeyValue(k, "$v --- $v")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<KStreamApplication>(*args)
}
