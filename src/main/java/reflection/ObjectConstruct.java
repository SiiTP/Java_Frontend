package reflection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Created by ivan on 26.10.15.
 */
public class ObjectConstruct {
    private static final Logger LOGGER = LogManager.getLogger(ObjectConstruct.class);
    @Nullable
    public static Object constructFromName(String className){
        Object o = null;
        try {
            Class<?> c = Class.forName(className);
            o = c.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.fatal("cant create instance ", e);
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
            LOGGER.fatal("cant set field value ",e);
        }
        field.setAccessible(false);
    }
}
