package io.github.fallOut015.pact_magic.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.client.renderer.entity.model.SigilModel;
import io.github.fallOut015.pact_magic.entity.effect.SigilEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class SigilRenderer extends EntityRenderer<SigilEntity> {
	final SigilModel<SigilEntity> model;
	static final ResourceLocation TEXTURE = new ResourceLocation("pact_magic", "textures/entity/sigil.png");
	
	public SigilRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.model = new SigilModel<>();
	}

	@Override
	public ResourceLocation getEntityTexture(SigilEntity entity) {
		return TEXTURE;
	}
	@Override
	public void render(SigilEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		
		IVertexBuilder vertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEXTURE));
		this.model.render(matrixStackIn, vertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}