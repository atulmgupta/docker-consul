package com.atulmgupta.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.client.RestTemplate
import java.time.Duration


@Configuration
class RestTemplateConfig {
//    @Value("\${server.ssl.key-store}")
//    private val trustStore: Resource? = null
//
//    @Value("\${server.ssl.key-store-password}")
//    private val trustStorePassword: String? = null

    @LoadBalanced
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(1000))
            .setReadTimeout(Duration.ofMillis(1000)).build()
    }


}