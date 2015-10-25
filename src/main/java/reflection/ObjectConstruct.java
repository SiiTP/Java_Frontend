package reflection;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Created by ivan on 26.10.15.
 */
public class ObjectConstruct {

    @Nullable
    public static Object constructFromName(String className){
        Object o = null;
        try {
            Class<?> c = Class.forName(className);
            o = c.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
    public static void setFields(Object object,String fieldName,Object fieldValue) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            if (field.getType() == String.class) {
                field.set(object, fieldValue);
            }
            if (field.getType() == int.class) {
                field.set(object, fieldValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
    }
}
