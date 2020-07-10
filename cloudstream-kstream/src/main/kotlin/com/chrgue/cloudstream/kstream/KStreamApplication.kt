package com.chrgue.cloudstream.kstream

import com.chrgue.cloudstream.kstream.model.Category
import com.chrgue.cloudstream.kstream.model.CategoryAggregate
import com.chrgue.cloudstream.kstream.model.CategoryFiltersInfo
import com.chrgue.cloudstream.kstream.model.Filter
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.*
import org.apache.kafka.streams.state.KeyValueStore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.serializer.JsonSerde
import java.util.function.BiFunction

@SpringBootApplication
class KStreamApplication {

    @Bean
    fun processor() = BiFunction<
            KStream<String, Category>, KStream<String, Filter>,
            KStream<String, CategoryAggregate>> { categories, filters ->

        val categoriesFilters = getCategoryFiltersTable(filters)
        val categories = getCategoriesTable(categories)

        categories
                .join(categoriesFilters, { it.id }) { category, categoryFiltersInfo ->
                    CategoryAggregate(id = category.id, name = category.name, filtersInfo = categoryFiltersInfo)
                }.toStream()
    }

    private fun getCategoriesTable(categories: KStream<String, Category>): KTable<String, Category> {
        return categories
                .selectKey { k, v -> v.id } // ensure we have o proper key
                .toTable(serdesForCategory())
    }

    private fun getCategoryFiltersTable(filters: KStream<String, Filter>): KTable<String, CategoryFiltersInfo>? {
        return filters
                .groupBy({ _, filter -> filter.categoryId }, serdesForFilter())
                .aggregate({ CategoryFiltersInfo() }, { _, filter, categoryFilters ->
                    aggregateCategoryInfo(categoryFilters, filter)
                }, serdesForCategoryFiltersInfo())
    }

    private fun aggregateCategoryInfo(currentCategoryInfo: CategoryFiltersInfo, filter: Filter): CategoryFiltersInfo {
        val filterRelation = currentCategoryInfo.allFilters + mapOf(filter.id to filter)
        val topFilters = filterRelation.values.toList().sortedByDescending(Filter::clickCount)
        return currentCategoryInfo.copy(allFilters = filterRelation, topFilters = topFilters)
    }

    /**
     * Serializers / Deserializers
     */

    private fun serdesForFilter() = Grouped.with(Serdes.String(), JsonSerde(Filter::class.java))

    private fun serdesForCategoryFiltersInfo(): Materialized<String, CategoryFiltersInfo, KeyValueStore<Bytes, ByteArray>>? =
            Materialized.with(Serdes.String(), JsonSerde(CategoryFiltersInfo::class.java))

    private fun serdesForCategory(): Materialized<String, Category, KeyValueStore<Bytes, ByteArray>>? =
            Materialized.with(Serdes.String(), JsonSerde(Category::class.java))
}

fun main(args: Array<String>) {
    runApplication<KStreamApplication>(*args)
}
