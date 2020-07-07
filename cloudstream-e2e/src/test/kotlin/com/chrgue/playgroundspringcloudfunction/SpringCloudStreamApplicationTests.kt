package com.chrgue.playgroundspringcloudfunction

import com.chrgue.playgroundspringcloudfunction.repository.CategoryAggregateRepository
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.TimeUnit


@Testcontainers
class SpringCloudStreamApplicationTests {

    @Container
    val mongoDBContainer = MongoDBContainer()

    @Test
    fun testLocalFilterProcessing() {
        val config = TestChannelBinderConfiguration.getCompleteConfiguration(SpringCloudStreamApplication::class.java)

        with(SpringApplicationBuilder(*config).run("--spring.cloud.function.definition=filterSource|filterProcessor|categorySink", "--spring.data.mongodb.port=${mongoDBContainer.firstMappedPort}")) {

            val repository = getBean(CategoryAggregateRepository::class.java)

            await().atMost(2, TimeUnit.SECONDS)
                    .untilAsserted { assertThat(repository.count()).isEqualTo(1) }
        }
    }

    @Test
    fun testLocalCategoryProcessing() {
        val config = TestChannelBinderConfiguration.getCompleteConfiguration(SpringCloudStreamApplication::class.java)

        with(SpringApplicationBuilder(*config).run("--spring.cloud.function.definition=categorySource|categoryProcessor|categorySink", "--spring.data.mongodb.port=${mongoDBContainer.firstMappedPort}")) {
            val repository = getBean(CategoryAggregateRepository::class.java)

            await().atMost(2, TimeUnit.SECONDS)
                    .untilAsserted { assertThat(repository.count()).isEqualTo(1) }
        }
    }
}
