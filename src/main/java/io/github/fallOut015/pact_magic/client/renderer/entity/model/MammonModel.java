package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.fallOut015.pact_magic.Main;
import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class MammonModel<T extends MammonEntity> extends EntityModel<T> {
	private final ModelRenderer mammon;
	private final ModelRenderer head;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;

	public MammonModel() {
		textureWidth = 32;
		textureHeight = 64;

		mammon = new ModelRenderer(this);
		mammon.setRotationPoint(0.0F, 24.0F, 0.0F);
		mammon.setTextureOffset(0, 0).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 10.0F, 4.0F, 0.0F, false);
		mammon.setTextureOffset(0, 14).addBox(-3.5F, -8.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);
		mammon.setTextureOffset(0, 14).addBox(0.5F, -8.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -18.0F, -0.3333F);
		mammon.addChild(head);
		head.setTextureOffset(0, 25).addBox(-3.0F, -6.0F, -2.6667F, 6.0F, 6.0F, 6.0F, 0.0F, false);
		head.setTextureOffset(0, 37).addBox(1.0F, -10.0F, -1.6667F, 1.0F, 4.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(0, 37).addBox(-2.0F, -10.0F, -1.6667F, 1.0F, 4.0F, 4.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(4.0F, -17.0F, 0.0F);
		mammon.addChild(leftArm);
		leftArm.setTextureOffset(12, 14).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-4.0F, -17.0F, 0.0F);
		mammon.addChild(rightArm);
		rightArm.setTextureOffset(12, 14).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		if(entity.isCasting()) {
			double x = entity.getAnimationFrames();
			this.leftArm.rotateAngleZ = (float) Main.quad(x, 50, -2, 4, true);
			this.rightArm.rotateAngleZ = (float) Main.quad(x, 50, 2, 4, true);
		} else {
			this.leftArm.rotateAngleZ = 0;
			this.rightArm.rotateAngleZ = 0;
		}
		
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
	}
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		mammon.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}