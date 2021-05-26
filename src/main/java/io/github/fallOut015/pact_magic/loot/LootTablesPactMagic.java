package io.github.fallOut015.pact_magic.loot;

import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Method;

public class LootTablesPactMagic {
    public static final ResourceLocation CHESTS_ANGEL_TEMPLE = register("chests/angel_temple");
    public static final ResourceLocation CHESTS_DEMON_TEMPLE = register("chests/demon_temple");



    private static ResourceLocation register(String name) {
        try {
            Method lootTables$register = LootTables.class.getDeclaredMethod("register", ResourceLocation.class);
            lootTables$register.setAccessible(true);
            return (ResourceLocation) lootTables$register.invoke(null, new ResourceLocation(MainPactMagic.MODID, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}