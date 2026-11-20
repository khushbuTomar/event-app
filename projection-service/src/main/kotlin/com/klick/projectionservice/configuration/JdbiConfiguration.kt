package com.klick.projectionservice.configuration

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbiConfiguration {

    @Bean
    fun provideJdbiDataSource(dataSource: DataSource) : Jdbi{

        return Jdbi.create(dataSource)
            .installPlugin(KotlinPlugin())
    }
}