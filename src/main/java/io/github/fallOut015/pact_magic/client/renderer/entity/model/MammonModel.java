package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MammonModel<T extends MammonEntity> extends EntityModel<T> {
	ModelRenderer head;
	
	public MammonModel() {
		this.head = new ModelRenderer(this);
		this.head.addBox(0, 0, 0, 16, 16, 16);
	}
	
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}