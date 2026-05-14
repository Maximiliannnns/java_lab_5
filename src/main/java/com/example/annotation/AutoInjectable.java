package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация-маркер для автоматического внедрения зависимостей.
 * 
 * Поля, помеченные этой аннотацией, будут автоматически инициализированы
 * классом {@link com.example.injector.Injector} на основе конфигурации
 * в файле {@code injector.properties}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjectable {
}