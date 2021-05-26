package io.github.fallOut015.pact_magic.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.JunglePyramidPiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DemonTempleStructure extends Structure<NoFeatureConfig> {
    public DemonTempleStructure(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return DemonTempleStructure.Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> p_i225807_1_, int p_i225807_2_, int p_i225807_3_, MutableBoundingBox p_i225807_4_, int p_i225807_5_, long p_i225807_6_) {
            super(p_i225807_1_, p_i225807_2_, p_i225807_3_, p_i225807_4_, p_i225807_5_, p_i225807_6_);
        }

        public void generatePieces(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int x, int z, Biome biome, NoFeatureConfig config) {
            AngelTemplePiece angeltemplepiece = new AngelTemplePiece(templateManager, null);
            this.pieces.add(angeltemplepiece);
            this.calculateBoundingBox();
        }
    }
}
