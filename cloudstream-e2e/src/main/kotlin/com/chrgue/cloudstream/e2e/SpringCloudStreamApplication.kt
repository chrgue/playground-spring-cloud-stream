package com.chrgue.cloudstream.e2e

import com.chrgue.cloudstream.e2e.model.CategoryAggregate
import com.chrgue.cloudstream.e2e.model.raw.Category
import com.chrgue.cloudstream.e2e.model.raw.Filter
import com.chrgue.cloudstream.e2e.repository.CategoryAggregateRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.repository.findByIdOrNull
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

@SpringBootApplication
@EnableMongoRepositories
class SpringCloudStreamApplication {

    @Bean
    fun categorySource() = Supplier {
        println("generate category")
        Category(id = "42", name = "Hello")
    }

    @Bean
    fun filterSource() = Supplier {
        println("generate filter")
        Filter(id = UUID.randomUUID().toString(), name = "filter name", categoryId = "42")
    }

    @Bean
    fun categoryProcessor(aggregateService: AggregateService) = Function<Category, CategoryAggregate> {
        aggregateService.createAggregate(it)
    }

    @Bean
    fun filterProcessor(aggregateService: AggregateService) = Function<Filter, CategoryAggregate> {
        aggregateService.createAggregate(it)
    }

    @Bean
    fun categorySink(categoryAggregateRepository: CategoryAggregateRepository): Consumer<CategoryAggregate> = Consumer { newAggregate ->

        val saved = categoryAggregateRepository.findByIdOrNull(newAggregate.id)?.let { existing ->

            val mergedAggregate = existing.apply {

                newAggregate.filters.takeIf { it.isNotEmpty() }?.let {
                    filters += it
                    filterCount = filters.size
                }

                newAggregate.name?.let { name = it }
            }

            categoryAggregateRepository.save(mergedAggregate)
        } ?: categoryAggregateRepository.save(newAggregate)


        println("category saved $saved")
    }
}

fun main(args: Array<String>) {
    runApplication<SpringCloudStreamApplication>(*args)
}
