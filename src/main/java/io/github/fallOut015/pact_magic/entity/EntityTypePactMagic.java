package io.github.fallOut015.pact_magic.entity;

import io.github.fallOut015.pact_magic.Main;
import io.github.fallOut015.pact_magic.entity.effect.SigilEntity;
import io.github.fallOut015.pact_magic.entity.effect.angels.SeraphEntity;
import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypePactMagic {
	private static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);
	
	public static final RegistryObject<EntityType<SeraphEntity>> SERAPH = ENTITIES.register("seraph", () -> EntityType.Builder.create(SeraphEntity::new, EntityClassification.MISC).build("seraph"));
	public static final RegistryObject<EntityType<MammonEntity>> MAMMON = ENTITIES.register("mammon", () -> EntityType.Builder.create(MammonEntity::new, EntityClassification.MISC).build("mammon"));
	public static final RegistryObject<EntityType<SigilEntity>> SIGIL = ENTITIES.register("sigil", () -> EntityType.Builder.create(SigilEntity::new, EntityClassification.MISC).build("sigil"));
	
	public static void register(IEventBus bus) {
		ENTITIES.register(bus);
	}
//	public static Supplier<EntityType<?>> supply(RegistryObject<EntityType<?>> entityType) {
//		return () -> entityType.get();
//	}
}