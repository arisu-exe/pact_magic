package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.entity.effect.SigilEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SigilModel<T extends SigilEntity> extends EntityModel<T> {
	ModelRenderer sigil;
	
	public SigilModel() {
		this.sigil = new ModelRenderer(this);
		this.sigil.addBox(-32, 0, -32, 64, 0, 64);
	}
	
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.sigil.rotateAngleY = ageInTicks / 2;
	}
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.sigil.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}