package com.example.injector;

import com.example.annotation.AutoInjectable;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
/**
 * Основной класс для внедрения зависимостей.
 * 
 * Использует механизм рефлексии для поиска полей, помеченных аннотацией
 * {@link AutoInjectable}, и инициализирует их реализациями, указанными
 * в файле {@code injector.properties}.
 * 
 * Поддерживает внедрение любых интерфейсов при условии наличия
 * конфигурации в properties-файле.
 *
 */
public class Injector {

    private Properties properties = new Properties();
    /**
     * Конструктор загружает конфигурацию из файла {@code injector.properties}.
     * 
     * @throws RuntimeException если файл конфигурации не найден или не может быть прочитан
     */
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
    /**
     * Выполняет внедрение зависимостей в переданный объект.
     * 
     * @param <T> тип объекта
     * @param object объект, в который нужно внедрить зависимости
     * @return тот же объект с инициализированными полями
     * @throws RuntimeException при ошибках рефлексии или отсутствии конфигурации
     */
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