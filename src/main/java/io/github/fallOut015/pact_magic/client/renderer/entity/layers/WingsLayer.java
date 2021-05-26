package io.github.fallOut015.pact_magic.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.pact_magic.client.renderer.entity.model.WingsModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class WingsLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("pact_magic", "textures/entity/wings.png");
	private final WingsModel wingsModel;

	public WingsLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
		super(entityRendererIn);
		
		this.wingsModel = new WingsModel(this.getParentModel());
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		//entitylivingbaseIn.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
			if(entitylivingbaseIn.isSkinLoaded() && !entitylivingbaseIn.isInvisible()) {
				IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
				int i = LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F);

				matrixStackIn.pushPose();
				this.wingsModel.leftWing.copyFrom(this.getParentModel().body);
				this.wingsModel.rightWing.copyFrom(this.getParentModel().body);
				this.wingsModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0f, 1.0f, 1.0f, 1.0f);
				matrixStackIn.popPose();
		    }
		//});
	}
}