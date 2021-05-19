package io.github.fallOut015.pact_magic.client.renderer.entity.model;
// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sun.print.resources.serviceui_zh_TW;

@OnlyIn(Dist.CLIENT)
public class WingsModel extends Model {
	public final ModelRenderer leftWing;
	public final ModelRenderer rightWing;

	public WingsModel(Model base) {
		super(RenderType::entityCutoutNoCull);

		texWidth = 16;
		texHeight = 16;

		leftWing = new ModelRenderer(base);
		leftWing.setPos(0.0F, 24.0F, 0.0F);
		leftWing.texOffs(0, 0).addBox(-7.0F, 3f, 4.0F, 6.0F, 16.0F, 0.0F, 0.0F, false);

		rightWing = new ModelRenderer(base);
		rightWing.setPos(0.0F, 0.0F, 0.0F);
		rightWing.texOffs(0, 0).addBox(1.0F, 3f, 4.0F, 6.0F, 16.0F, 0.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		leftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		rightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}