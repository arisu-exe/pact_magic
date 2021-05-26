package io.github.fallOut015.pact_magic.world.gen.feature.structure;

import io.github.fallOut015.pact_magic.MainPactMagic;
import io.github.fallOut015.pact_magic.loot.LootTablesPactMagic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class AngelTemplePiece extends TemplateStructurePiece {
    private final ResourceLocation templateLocation;
    private final Rotation rotation;

    private static final ResourceLocation STRUCTURE_LOCATION_ANGEL_TEMPLE = new ResourceLocation(MainPactMagic.MODID, "angel_temple");

    public AngelTemplePiece(TemplateManager templateManager, BlockPos pos, Rotation rot) {
        super(IStructurePieceTypePactMagic.ANGEL_TEMPLE_PIECE, 0);
        this.templateLocation = STRUCTURE_LOCATION_ANGEL_TEMPLE;
        BlockPos blockpos = pos;
        this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        this.rotation = rot;
        this.loadTemplate(templateManager);
    }
    public AngelTemplePiece(TemplateManager templateManager, CompoundNBT tag) {
        super(IStructurePieceTypePactMagic.ANGEL_TEMPLE_PIECE, tag);
        this.templateLocation = new ResourceLocation(tag.getString("Template"));
        this.rotation = Rotation.valueOf(tag.getString("Rot"));
        this.loadTemplate(templateManager);
    }

    private void loadTemplate(TemplateManager templateManager) {
        Template template = templateManager.getOrCreate(this.templateLocation);
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        this.setup(template, this.templatePosition, placementsettings);
    }
    @Override
    protected void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Template", this.templateLocation.toString());
        tag.putString("Rot", this.rotation.name());
    }
    @Override
    public boolean postProcess(ISeedReader level, StructureManager structureManager, ChunkGenerator chunkGenerator, Random rand, MutableBoundingBox bb, ChunkPos chunkPos, BlockPos blockPos) {
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setRotationPivot(blockPos).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        BlockPos blockpos = BlockPos.ZERO;
        BlockPos blockpos1 = this.templatePosition.offset(Template.calculateRelativePosition(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
        int i = level.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
        BlockPos blockpos2 = this.templatePosition;
        this.templatePosition = this.templatePosition.offset(0, i - 90 - 1, 0);
        boolean flag = super.postProcess(level, structureManager, chunkGenerator, rand, bb, chunkPos, blockPos);
        if (this.templateLocation.equals(STRUCTURE_LOCATION_ANGEL_TEMPLE)) {
            BlockPos blockpos3 = this.templatePosition.offset(Template.calculateRelativePosition(placementsettings, new BlockPos(3, 0, 5)));
            BlockState blockstate = level.getBlockState(blockpos3.below());
            /*if (!blockstate.isAir() && !blockstate.is(Blocks.LADDER)) {
                level.setBlock(blockpos3, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
            }*/
        }

        this.templatePosition = blockpos2;
        return flag;
    }
    @Override
    protected void handleDataMarker(String marker, BlockPos pos, IServerWorld level, Random rand, MutableBoundingBox box) {
        /*if ("chest".equals(marker)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            TileEntity tileentity = level.getBlockEntity(pos.below());
            if (tileentity instanceof ChestTileEntity) {
                ((ChestTileEntity)tileentity).setLootTable(LootTablesPactMagic.CHESTS_ANGEL_TEMPLE, rand.nextLong());
            }
        }*/
    }
}