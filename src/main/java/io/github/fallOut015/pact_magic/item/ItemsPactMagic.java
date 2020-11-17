package io.github.fallOut015.pact_magic.item;

import io.github.fallOut015.pact_magic.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsPactMagic {
	private static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
	
	public static RegistryObject<Item> BOOK_OF_ANGELS = ITEMS.register("book_of_angels", () -> new BookOfAngelsItem(new Item.Properties().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON).maxStackSize(1)));
	public static RegistryObject<Item> BOOK_OF_DEMONS = ITEMS.register("book_of_demons", () -> new BookOfDemonsItem(new Item.Properties().group(ItemGroup.MISC).rarity(RarityPactMagic.DEVILISH).maxStackSize(1)));
	
	public static RegistryObject<Item> ANGEL_PAGE = ITEMS.register("angel_page", () -> new AngelPageItem(new Item.Properties().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON).maxStackSize(1)));
	public static RegistryObject<Item> DEMON_PAGE = ITEMS.register("demon_page", () -> new DemonPageItem(new Item.Properties().group(ItemGroup.MISC).rarity(RarityPactMagic.DEVILISH).maxStackSize(1)));
	
	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}