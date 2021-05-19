package io.github.fallOut015.pact_magic.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupPactMagic {
    public static final ItemGroup PACT_MAGIC = new ItemGroup("pact_magic") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ItemsPactMagic.BOOK_OF_ANGELS.get());
        }
    };
}