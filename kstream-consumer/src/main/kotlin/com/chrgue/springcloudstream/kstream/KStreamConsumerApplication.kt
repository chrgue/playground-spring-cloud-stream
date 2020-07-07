package com.chrgue.springcloudstream.kstream

import org.apache.kafka.streams.kstream.KStream
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Consumer

@SpringBootApplication
class KStreamConsumerApplication {


    @Bean
    fun processor() = Consumer<KStream<String, String>> {
        it.foreach { k, v ->
            println("$k --- $v")
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KStreamConsumerApplication>(*args)
}
