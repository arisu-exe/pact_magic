package io.github.fallOut015.pact_magic.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.client.renderer.entity.model.HaloModel;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class HaloLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("two", "textures/entity/halo.png");
	private final HaloModel haloModel;

	public HaloLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
		super(entityRendererIn);
		
		this.haloModel = new HaloModel(this.getEntityModel());
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		entitylivingbaseIn.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
			if(entitylivingbaseIn.hasSkin() && !entitylivingbaseIn.isInvisible()) {
				IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(TEXTURE));
				int i = LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F);

				matrixStackIn.push();
				this.haloModel.halo.copyModelAngles(this.getEntityModel().bipedHead);
				this.haloModel.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 0, 0, 0, 0);
				matrixStackIn.pop();
		    }
		});
	}
}