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
	
	public static final RegistryObject<EntityType<SeraphEntity>> SERAPH = ENTITIES.register("seraph", () -> EntityType.Builder.of(SeraphEntity::new, EntityClassification.MISC).sized(1.0f, 2.0f).build("seraph"));
	public static final RegistryObject<EntityType<MammonEntity>> MAMMON = ENTITIES.register("mammon", () -> EntityType.Builder.of(MammonEntity::new, EntityClassification.MISC).sized(0.8f, 1.5f).setShouldReceiveVelocityUpdates(true).build("mammon"));
	public static final RegistryObject<EntityType<SigilEntity>> SIGIL = ENTITIES.register("sigil", () -> EntityType.Builder.of(SigilEntity::new, EntityClassification.MISC).sized(2.0f, 0.0625f).noSave().build("sigil"));
	
	public static void register(IEventBus bus) {
		ENTITIES.register(bus);
	}
//	public static Supplier<EntityType<?>> supply(RegistryObject<EntityType<?>> entityType) {
//		return () -> entityType.get();
//	}
}