package com.zolinahoczki.carefinderapi.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.DbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class AppMongoConfig {
    @Autowired
    private val mongoDbFactory: MongoDatabaseFactory? = null

    @Autowired
    private val mongoMappingContext: MongoMappingContext? = null

    @Bean
    fun mappingMongoConverter(): MappingMongoConverter {
        val dbRefResolver: DbRefResolver = DefaultDbRefResolver(mongoDbFactory!!)
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext!!)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }
}