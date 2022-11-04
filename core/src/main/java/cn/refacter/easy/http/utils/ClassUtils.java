package cn.refacter.easy.http.utils;

/**
 * @author refacter
 * Date：Create in 2022/11/4 20:29
 */
public class ClassUtils {
    
    public static boolean isCustomClass(Class clazz) {
        if (java.lang.Integer.class.equals(clazz) || java.lang.Byte.class.equals(clazz)
                || java.lang.Long.class.equals(clazz) || java.lang.Double.class.equals(clazz)
                || java.lang.Float.class.equals(clazz) || java.lang.Character.class.equals(clazz)
                || java.lang.Short.class.equals(clazz) || java.lang.Boolean.class.equals(clazz)
                || java.lang.String.class.equals(clazz)) {
            return false;
        }
        else {
            return true;
        }
    }
}
