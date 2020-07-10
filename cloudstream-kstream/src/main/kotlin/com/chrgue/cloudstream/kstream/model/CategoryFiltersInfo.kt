package com.chrgue.cloudstream.kstream.model

data class CategoryFiltersInfo(val allFilters:Map<String,Filter> = emptyMap(),
                               val topFilters:List<Filter> = emptyList() )