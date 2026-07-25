package com.pedrin.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.pedrin.pedidos.client")
public class ClientsConfig {
}
