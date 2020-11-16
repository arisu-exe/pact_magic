package io.github.fallOut015.pact_magic.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.client.renderer.entity.model.MammonModel;
import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MammonRenderer extends EntityRenderer<MammonEntity> {
	final MammonModel<MammonEntity> model;
	static final ResourceLocation TEXTURE = new ResourceLocation("pact_magic", "textures/entity/demons/mammon.png");
	
	public MammonRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.model = new MammonModel<>();
	}

	@Override
	public ResourceLocation getEntityTexture(MammonEntity entity) {
		return TEXTURE;
	}
	@Override
	public void render(MammonEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		
		IVertexBuilder vertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEXTURE));
		this.model.render(matrixStackIn, vertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}