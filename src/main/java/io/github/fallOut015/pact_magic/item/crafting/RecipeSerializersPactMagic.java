package io.github.fallOut015.pact_magic.item.crafting;

import io.github.fallOut015.pact_magic.Main;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersPactMagic {
	private static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);



	public static RegistryObject<SpecialRecipeSerializer<BookOfAngelsRecipe>> BOOK_OF_ANGELS = RECIPE_SERIALIZERS.register("book_of_angels", () -> new SpecialRecipeSerializer<BookOfAngelsRecipe>(BookOfAngelsRecipe::new));
	public static RegistryObject<SpecialRecipeSerializer<BookOfDemonsRecipe>> BOOK_OF_DEMONS = RECIPE_SERIALIZERS.register("book_of_demons", () -> new SpecialRecipeSerializer<BookOfDemonsRecipe>(BookOfDemonsRecipe::new));



	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
}
