package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.entity.effect.SigilEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SigilModel<T extends SigilEntity> extends EntityModel<T> {
	ModelRenderer sigil;
	
	public SigilModel() {
		super(RenderType::entityTranslucentCull);
		
		this.texWidth = 128;
		this.texHeight = 64;
		
		this.sigil = new ModelRenderer(this);
		this.sigil.addBox(-32, 0, -32, 64, 0.0625f, 64);
		this.sigil.setPos(0, 0, 0);
	}
	
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.sigil.yRot = ageInTicks / 15;
	}
	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.sigil.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}