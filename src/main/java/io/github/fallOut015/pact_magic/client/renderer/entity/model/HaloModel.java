package io.github.fallOut015.pact_magic.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class HaloModel extends Model {
	public final ModelRenderer halo;

	public HaloModel(Model base) {
	    super(RenderType::entitySolid);

		texWidth = 16;
		texHeight = 16;

		halo = new ModelRenderer(base);
		halo.setPos(0.0F, 24.0F, 0.0F);
		halo.texOffs(0, 0).addBox(-4.0F, -11F, -4.0F, 8.0F, 1.0F, 0.0F, 0.0F, false);
		halo.texOffs(0, 0).addBox(-4.0F, -1.0F, 4.0F, 8.0F, 1.0F, 0.0F, 0.0F, false);
		halo.texOffs(0, 0).addBox(4.0F, -6.0F, -4.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);
		halo.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		halo.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}