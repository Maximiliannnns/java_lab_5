import com.example.bean.SomeBean;
import com.example.injector.Injector;
/**
 * Главный класс приложения для демонстрации работы внедрения зависимостей.
 * 
 * Создаёт объект {@link SomeBean} и внедряет в него зависимости
 * с помощью {@link Injector}.
 */
public class Main {
    public static void main(String[] args) {
        SomeBean sb = new Injector().inject(new SomeBean());
        sb.foo();                   

        // меняем в properties SomeImpl на OtherImpl и должно стать BC
    }
}