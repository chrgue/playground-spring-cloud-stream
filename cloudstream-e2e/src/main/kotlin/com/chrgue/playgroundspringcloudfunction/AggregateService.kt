package com.chrgue.playgroundspringcloudfunction

import com.chrgue.playgroundspringcloudfunction.model.raw.Category
import com.chrgue.playgroundspringcloudfunction.model.CategoryAggregate
import com.chrgue.playgroundspringcloudfunction.model.raw.Filter
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