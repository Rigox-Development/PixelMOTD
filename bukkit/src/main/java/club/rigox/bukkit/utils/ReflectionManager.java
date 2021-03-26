package club.rigox.bukkit.utils;

import java.lang.reflect.Field;

import club.rigox.bukkit.PixelMOTD;
import sun.misc.Unsafe;

public class ReflectionManager {
    private final PixelMOTD plugin;
    public ReflectionManager(PixelMOTD plugin) {
        this.plugin = plugin;
    }
    public void changeField(Object obj, String name, Object value) throws Throwable {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(!field.isAccessible());
    }

    @SuppressWarnings("deprecation")
    public void changeStaticFinalField(Object obj, String name, Object value) throws Throwable {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            field.set(null, value);
            modifiers.setAccessible(!modifiers.isAccessible());
            field.setAccessible(!field.isAccessible());
        } catch (Throwable throwable) {
            if (throwable.getCause() instanceof NoSuchFieldException && throwable.getCause().getMessage().equals("modifiers")) {
                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Unsafe unsafe = (Unsafe)unsafeField.get(null);
                Field field = obj.getClass().getDeclaredField(name);
                Object staticFieldBase = unsafe.staticFieldBase(field);
                long staticFieldOffset = unsafe.staticFieldOffset(field);
                unsafe.putObject(staticFieldBase, staticFieldOffset, value);
            }
            plugin.getLogs().error(throwable);
        }
    }
}
