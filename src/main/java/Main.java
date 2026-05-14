import com.example.bean.SomeBean;
import com.example.injector.Injector;

public class Main {
    public static void main(String[] args) {
        SomeBean sb = new Injector().inject(new SomeBean());
        sb.foo();                    // должно вывести AC

        // Для проверки смены реализации:
        // поменяй в properties SomeImpl на OtherImpl → должно стать BC
    }
}