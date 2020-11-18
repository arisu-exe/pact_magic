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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

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
		
		matrixStackIn.push();
		
		float alpha = 1.0f;

		if(entityIn.isCasting()) {
			float x = entityIn.getAnimationFrames();
			matrixStackIn.translate(0, -0.005f * (x - 35f) * x, 0);
			matrixStackIn.rotate(new Quaternion(Vector3f.YN, 8 * x, true));
			alpha = Math.max(0, -0.0125f * x + 1f);
		} else {
			matrixStackIn.translate(MathHelper.sin((float) entityIn.ticksExisted / 16f) * 0.5f, MathHelper.sin((float) entityIn.ticksExisted / 8f) * 0.25f, 0);
		}
		matrixStackIn.translate(0, 2f, 0);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180.0f));
		
		IVertexBuilder vertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEXTURE));
		this.model.setRotationAngles(entityIn, 0, 0, entityIn.ticksExisted, 0, 0);
		this.model.render(matrixStackIn, vertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, alpha);
	
		matrixStackIn.pop();
	}
}