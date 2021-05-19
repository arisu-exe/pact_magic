package io.github.fallOut015.pact_magic.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.client.renderer.entity.model.SeraphModel;
import io.github.fallOut015.pact_magic.entity.effect.angels.SeraphEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class SeraphRenderer extends EntityRenderer<SeraphEntity> {
	final SeraphModel<SeraphEntity> model;
	static final ResourceLocation TEXTURE = new ResourceLocation("pact_magic", "textures/entity/angels/seraph.png");
	
	public SeraphRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.model = new SeraphModel<>();
	}

	@Override
	public ResourceLocation getTextureLocation(SeraphEntity entity) {
		return TEXTURE;
	}
	@Override
	public void render(SeraphEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		
		IVertexBuilder vertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE));
		this.model.renderToBuffer(matrixStackIn, vertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}