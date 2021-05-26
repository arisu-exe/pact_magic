package io.github.fallOut015.pact_magic.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class AngelTempleStructure extends Structure<NoFeatureConfig> {
    private static final ResourceLocation STRUCTURE_LOCATION_ANGEL_TEMPLE = new ResourceLocation(MainPactMagic.MODID, "angel_temple");

    public AngelTempleStructure(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return AngelTempleStructure.Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int x, int z, MutableBoundingBox bb, int p_i225807_5_, long p_i225807_6_) {
            super(structure, x, z, bb, p_i225807_5_, p_i225807_6_);
        }

        public void generatePieces(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos pos = new BlockPos(x, chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG), z);

            JigsawManager.addPieces(dynamicRegistries, new VillageConfig(() -> dynamicRegistries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(STRUCTURE_LOCATION_ANGEL_TEMPLE), 10), AbstractVillagePiece::new, chunkGenerator, templateManager, pos, this.pieces, this.random,false, true);

            this.calculateBoundingBox();
        }
    }
}