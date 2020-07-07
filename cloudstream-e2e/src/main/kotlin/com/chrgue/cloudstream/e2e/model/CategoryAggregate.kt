package com.chrgue.cloudstream.e2e.model

import com.chrgue.cloudstream.e2e.model.raw.Filter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CategoryAggregate(@Id val id: String? = null,
                             var name: String? = null,
                             var filterCount: Int = -1,
                             var filters: Map<String, Filter> = emptyMap())