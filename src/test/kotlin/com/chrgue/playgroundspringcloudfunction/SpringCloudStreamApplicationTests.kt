package com.chrgue.playgroundspringcloudfunction

import com.chrgue.playgroundspringcloudfunction.model.raw.Category
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.integration.support.MessageBuilder


@SpringBootTest
class SpringCloudStreamApplicationTests {

    @Test
    fun whenSendMessage_thenResponseShouldUpdateText() {

    }
}
