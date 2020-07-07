package com.chrgue.playgroundspringcloudfunction.model

import com.chrgue.playgroundspringcloudfunction.model.raw.Filter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CategoryAggregate(@Id val id: String? = null,
                             var name: String? = null,
                             var filterCount: Int = -1,
                             var filters: Map<String, Filter> = emptyMap())