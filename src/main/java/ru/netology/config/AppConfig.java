package ru.netology.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru/netology/controller")
public class AppConfig {
    // Нет необходимости создавать бины явно, Spring автоматически сканирует и создает компоненты.
}
