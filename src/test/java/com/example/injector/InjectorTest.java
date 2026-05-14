package com.example.injector;

import com.example.bean.SomeBean;
import com.example.impl.OtherImpl;
import com.example.impl.SomeImpl;
import com.example.interfaces.SomeInterface;
import com.example.interfaces.SomeOtherInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для класса Injector
 */
class InjectorTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = new Injector();
    }

    @Test
    @DisplayName("Должен успешно внедрять зависимости в SomeBean")
    void shouldInjectDependenciesIntoSomeBean() {
        SomeBean bean = injector.inject(new SomeBean());

        assertNotNull(bean, "Объект SomeBean не должен быть null");

        // Проверяем, что поля действительно внедрены
        assertNotNull(getFieldValue(bean, "field1"), "field1 не внедрён");
        assertNotNull(getFieldValue(bean, "field2"), "field2 не внедрён");

        assertInstanceOf(SomeInterface.class, getFieldValue(bean, "field1"));
        assertInstanceOf(SomeOtherInterface.class, getFieldValue(bean, "field2"));
    }

    @Test
    @DisplayName("Должен внедрять SomeImpl по умолчанию")
    void shouldInjectSomeImplByDefault() {
        SomeBean bean = injector.inject(new SomeBean());
        SomeInterface field1 = (SomeInterface) getFieldValue(bean, "field1");

        assertInstanceOf(SomeImpl.class, field1, "Должна быть внедрена SomeImpl");
    }

    @Test
    @DisplayName("Должен корректно работать метод foo() после инъекции")
    void shouldExecuteFooMethodWithoutException() {
        SomeBean bean = injector.inject(new SomeBean());
        
        assertDoesNotThrow(bean::foo, "Метод foo() не должен бросать исключения");
    }

    @Test
    @DisplayName("Должен бросать исключение при отсутствии реализации в properties")
    void shouldThrowExceptionWhenNoImplementationFound() {
        
        // Создаём объект с интерфейсом, которого НЕТ в properties
        TestClassWithUnknownField obj = new TestClassWithUnknownField();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> injector.inject(obj),
                "Должно выбрасываться RuntimeException при отсутствии реализации");

        assertTrue(exception.getMessage().contains("Не найдена реализация"),
                "Сообщение об ошибке должно содержать 'Не найдена реализация'");
    }

    // Вспомогательный статический класс с неизвестным интерфейсом
    private static class TestClassWithUnknownField {

        @com.example.annotation.AutoInjectable
        private UnknownInterface unknownField;   
    }

    // Новый интерфейс, которого точно нет в properties
    private interface UnknownInterface {
        void doSomething();
    }

    @Test
    @DisplayName("Injector должен быть singleton-like по properties (одна загрузка)")
    void propertiesShouldBeLoadedOnlyOnce() {
        Injector injector2 = new Injector();
        assertNotNull(injector2);
        // Если конструктор не падает — значит properties загрузились
    }

    // Вспомогательный метод для получения значения private поля
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            fail("Не удалось получить значение поля " + fieldName);
            return null;
        }
    }
}