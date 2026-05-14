package com.example.bean;

import com.example.annotation.AutoInjectable;
import com.example.interfaces.SomeInterface;
import com.example.interfaces.SomeOtherInterface;
/**
 * Пример класса, в который автоматически внедряются зависимости.
 * 
 * Демонстрирует использование аннотации {@link AutoInjectable}.
 * Поля {@code field1} и {@code field2} инициализируются классом
 * {@link com.example.injector.Injector}.
 */
public class SomeBean {

    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;
    /**
     * Метод, демонстрирующий работу внедрённых зависимостей.
     * 
     * Вызывает методы внедрённых реализаций интерфейсов.
     * Ожидаемый вывод: "AC" или "BC" в зависимости от конфигурации.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();   
        System.out.println();   
    }
}
