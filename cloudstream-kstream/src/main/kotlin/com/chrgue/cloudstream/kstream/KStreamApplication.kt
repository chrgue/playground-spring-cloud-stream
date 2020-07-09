package com.chrgue.cloudstream.kstream

import com.chrgue.cloudstream.kstream.model.CategoryAggregate
import com.chrgue.cloudstream.kstream.model.Filter
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.serializer.JsonSerde
import java.util.function.Function

@SpringBootApplication
class KStreamApplication {

    @Bean
    fun processor() = Function<KStream<String, Filter>, KStream<String, CategoryAggregate>> {
        it.groupBy({ _, filter -> filterGroupBy(filter) }, Grouped.with(Serdes.String(), JsonSerde(Filter::class.java)))
                .aggregate(this::initializeCategoryFilters, { categoryId, filter, categoryFilters ->
                    categoryFilters(categoryFilters, filter, categoryId)
                }, Materialized.with(Serdes.String(), JsonSerde(CategoryAggregate::class.java)))
                .toStream()
    }

    private fun filterGroupBy(v: Filter) = v.categoryId

    private fun categoryFilters(categoryAggregate: CategoryAggregate, filter: Filter, categoryId: String): CategoryAggregate {
        val filterRelation = categoryAggregate.allFilters + mapOf(filter.id to filter)
        val topFilters = filterRelation.values.toList().sortedByDescending(Filter::clickCount)
        return categoryAggregate.copy(categoryId = categoryId, allFilters = filterRelation, topFilters = topFilters)
    }

    private fun initializeCategoryFilters() = CategoryAggregate()
}

fun main(args: Array<String>) {
    runApplication<KStreamApplication>(*args)
}
