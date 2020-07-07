package com.chrgue.cloudstream.e2e.model.raw

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Category(@Id val id: String? = null,
                    val name: String = "")