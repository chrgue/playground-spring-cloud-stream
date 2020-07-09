package com.chrgue.cloudstream.kstream.model

data class CategoryAggregate(val categoryId:String="",
                             val allFilters:Map<String,Filter> = emptyMap(),
                             val topFilters:List<Filter> = emptyList() )