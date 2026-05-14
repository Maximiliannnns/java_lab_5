package com.example.injector;

import com.example.annotation.AutoInjectable;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {

    private Properties properties = new Properties();

    public Injector() {
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("injector.properties")) {
            if (is == null) {
                throw new RuntimeException("Файл injector.properties не найден в resources!");
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки properties", e);
        }
    }

    public <T> T inject(T object) {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                field.setAccessible(true);

                String interfaceName = field.getType().getName();

                String implClassName = properties.getProperty(interfaceName);
                if (implClassName == null) {
                    throw new RuntimeException("Не найдена реализация для " + interfaceName);
                }

                try {
                    Class<?> implClass = Class.forName(implClassName);
                    Object implInstance = implClass.getDeclaredConstructor().newInstance();

                    field.set(object, implInstance);
                } catch (Exception e) {
                    throw new RuntimeException("Не удалось создать экземпляр " + implClassName, e);
                }
            }
        }
        return object;
    }
}	