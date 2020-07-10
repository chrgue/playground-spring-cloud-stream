package com.chrgue.cloudstream.kstream.model

data class CategoryAggregate(val id: String = "",
                             val name: String = "",
                             val filtersInfo: CategoryFiltersInfo? = null)