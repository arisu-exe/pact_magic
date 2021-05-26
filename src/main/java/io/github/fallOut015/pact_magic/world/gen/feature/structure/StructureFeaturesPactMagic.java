package io.github.fallOut015.pact_magic.world.gen.feature.structure;

import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureFeaturesPactMagic {
    private static final DeferredRegister<Structure<?>> STRUCTURE_FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MainPactMagic.MODID);



    public static final RegistryObject<Structure<NoFeatureConfig>> ANGEL_TEMPLE = STRUCTURE_FEATURES.register("angel_temple", () -> new AngelTempleStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> DEMON_TEMPLE = STRUCTURE_FEATURES.register("demon_temple", () -> new DemonTempleStructure(NoFeatureConfig.CODEC));



    public static void register(IEventBus bus) {
        STRUCTURE_FEATURES.register(bus);
    }
}