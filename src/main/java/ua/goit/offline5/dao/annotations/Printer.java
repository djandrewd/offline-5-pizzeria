package ua.goit.offline5.dao.annotations;

import java.lang.reflect.Field;

/**
 * Created by andreymi on 3/10/2017.
 */
public class Printer {
    public static String print(TestClassA value) {
        Class<TestClassA> clazz = TestClassA.class;
        Field field = null;
        try {
            field = clazz.getDeclaredField("a");
            field.setAccessible(true);
            Precision precision = field.getAnnotation(Precision.class);
            return String.format("%." + precision.param() + "d", field.get(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
