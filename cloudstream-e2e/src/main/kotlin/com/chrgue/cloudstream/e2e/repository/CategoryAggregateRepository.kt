package com.chrgue.cloudstream.e2e.repository

import com.chrgue.cloudstream.e2e.model.CategoryAggregate
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryAggregateRepository : MongoRepository<CategoryAggregate, String>