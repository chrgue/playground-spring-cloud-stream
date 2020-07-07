package com.chrgue.cloudstream.e2e

import com.chrgue.cloudstream.e2e.model.raw.Category
import com.chrgue.cloudstream.e2e.model.CategoryAggregate
import com.chrgue.cloudstream.e2e.model.raw.Filter
import org.springframework.stereotype.Component

@Component
class AggregateService {

    fun createAggregate(filter: Filter): CategoryAggregate {
        val filters = mapOf(filter.id!! to filter)
        return CategoryAggregate(id = filter.categoryId, filterCount = filters.size, filters = filters)
    }

    fun createAggregate(category: Category): CategoryAggregate {
        return CategoryAggregate(id = category.id, name = category.name)
    }
}