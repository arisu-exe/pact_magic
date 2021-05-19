package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.entity.effect.angels.SeraphEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SeraphModel<T extends SeraphEntity> extends EntityModel<T> {
	ModelRenderer head;
	
	public SeraphModel() {
		this.head = new ModelRenderer(this);
		this.head.addBox(0, 0, 0, 16, 16, 16);
	}
	
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}