package org.dimdev.rift.mixin.hook.client;

import io.github.tivj.interfaces.KeyBindingCategories;
import io.github.tivj.interfaces.KeyBindingHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(KeyBinding.class)
public class MixinKeybind implements KeyBindingCategories, KeyBindingHelper {
    @Shadow
    private InputMappings.Input keyCode;
    @Shadow
    private static Map<String, Integer> CATEGORY_ORDER;

    @Override
    public void addCategory(String name, int order) {
        CATEGORY_ORDER.put(name,order);
    }

    @Override
    public int getKeyCode() {
        return keyCode.getKeyCode();
    }

    @Override
    public void addNextAvailableCategory(String name, int order) {
        int whereToAdd = order;
        boolean needsToLoop = true;
        while (needsToLoop) {
            if (CATEGORY_ORDER.containsValue(whereToAdd)) {
                whereToAdd++;
                needsToLoop = true;
            } else needsToLoop = false;
        }
        if (!needsToLoop) {
            LogManager.getLogger().info("Registering "+name+" key category to index "+whereToAdd);
            addCategory(name, whereToAdd);
        }
    }

    @Override
    public void moveCategory(String name, int newPosition) {
        CATEGORY_ORDER.remove(name);
        this.addCategory(name, newPosition);
    }
}
