package io.github.fallOut015.pact_magic.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.client.renderer.entity.model.HaloModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class HaloLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("pact_magic", "textures/entity/halo.png");
	private final HaloModel haloModel;

	public HaloLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
		super(entityRendererIn);
		
		this.haloModel = new HaloModel(this.getParentModel());
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		//entitylivingbaseIn.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
			if(entitylivingbaseIn.isSkinLoaded() && !entitylivingbaseIn.isInvisible()) {
				IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entitySolid(TEXTURE));
				int i = LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F);

				matrixStackIn.pushPose();
				this.haloModel.halo.copyFrom(this.getParentModel().head);
				this.haloModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0f, 1.0f, 1.0f, 1.0f);
				matrixStackIn.popPose();
		    }
		//});
	}
}