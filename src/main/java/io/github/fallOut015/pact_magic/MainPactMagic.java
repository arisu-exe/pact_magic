package io.github.fallOut015.pact_magic;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import io.github.fallOut015.pact_magic.world.gen.feature.structure.StructureFeaturesPactMagic;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.client.registry.RenderingRegistryPactMagic;
import io.github.fallOut015.pact_magic.client.renderer.entity.EntityRendererManagerPactMagic;
import io.github.fallOut015.pact_magic.common.angels.Angels;
import io.github.fallOut015.pact_magic.common.angels.IToggleable;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.PactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.PactMagicProvider;
import io.github.fallOut015.pact_magic.common.demons.Demons;
import io.github.fallOut015.pact_magic.entity.EntitiesPactMagic;
import io.github.fallOut015.pact_magic.item.ItemsPactMagic;
import io.github.fallOut015.pact_magic.item.crafting.RecipeSerializersPactMagic;
import io.github.fallOut015.pact_magic.server.JumpPacketHandler;
import io.github.fallOut015.pact_magic.server.PacketHandlerPactMagic;
import io.github.fallOut015.pact_magic.server.SpellCastPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.function.Supplier;

@Mod(MainPactMagic.MODID)
public class MainPactMagic {
	public static final String MODID = "pact_magic";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ResourceLocation BORDER = new ResourceLocation(MainPactMagic.MODID, "textures/gui/border.png");
    public static final ResourceLocation ANGEL_INDICATOR = new ResourceLocation(MainPactMagic.MODID, "textures/gui/angel_indicator.png");
    public static final ResourceLocation DEMON_INDICATOR = new ResourceLocation(MainPactMagic.MODID, "textures/gui/demon_indicator.png");
	public static final ResourceLocation BUFF = new ResourceLocation(MainPactMagic.MODID, "textures/gui/buff.png");
	public static final ResourceLocation DEBUFF = new ResourceLocation(MainPactMagic.MODID, "textures/gui/debuff.png");
	public static final ResourceLocation NONE = new ResourceLocation(MainPactMagic.MODID, "textures/gui/none.png");

    static @Nullable ServerPlayerEntity player;
    static double aw;
    static double ax;
    static double ay;
    static double dw;
    static double dx;
    static double dy;
    static boolean control;
    static boolean alt;
    
    static {
    	MainPactMagic.player = null;
    	MainPactMagic.aw = 0;
    	MainPactMagic.ax = 0;
    	MainPactMagic.ay = 0;
    	MainPactMagic.dw = 0;
    	MainPactMagic.dx = 0;
    	MainPactMagic.dy = 0;
    	MainPactMagic.control = false;
    	MainPactMagic.alt = false;
    }

    public MainPactMagic() {
    	ItemsPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	EntitiesPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	RecipeSerializersPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
		StructureFeaturesPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
    	PacketHandlerPactMagic.setup(event);
    	Angels.setup(event);
    	Demons.setup(event);
    	CapabilitiesPactMagic.setup(event);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
    	RenderingRegistryPactMagic.doClientStuff(event);
    	EntityRendererManagerPactMagic.doClientStuff(event);
    }
    private void enqueueIMC(final InterModEnqueueEvent event) { }
    private void processIMC(final InterModProcessEvent event) { }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) { }
    
    @Mod.EventBusSubscriber
    public static class Events {
    	@SuppressWarnings("deprecation")
		@SubscribeEvent
    	@OnlyIn(Dist.CLIENT)
    	public static void onRenderGameOverlay(final RenderGameOverlayEvent event) {
    		if(event.getType() == ElementType.ALL) {
    			if(MainPactMagic.player != null) {
    				MainPactMagic.player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
    	    			Minecraft minecraft = Minecraft.getInstance();
    	    			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
    	    			if(pactMagic.getSlottedAngel() != null) {
    	        			if(pactMagic.getSlottedAngel().isPrepared()) {
    	        				if(!minecraft.isPaused()) {
    	        					MainPactMagic.aw += 0.05;
    	        				}
        	        			MainPactMagic.ax = (Math.sin((MainPactMagic.aw * Math.PI) / 4) * 4);
        	        			MainPactMagic.ay = (Math.sin((MainPactMagic.aw * Math.PI) / 2) * 2);
    	        			} else {
    	        				MainPactMagic.aw = 0;
    	        				MainPactMagic.ax = 0;
    	        				MainPactMagic.ay = 0;
    	        			}
    	        			
    	        			if(pactMagic.getSlottedAngel() instanceof IToggleable) {
    	        				if(((IToggleable) pactMagic.getSlottedAngel()).isOn()) {
    	        					event.getMatrixStack().pushPose();
    	    	        			RenderSystem.enableBlend();
    	    	    	            RenderSystem.defaultBlendFunc();
    	    	        			minecraft.getTextureManager().bind(ANGEL_INDICATOR);
    	            				event.getMatrixStack().translate(32, 32, 0);
    	    	        			event.getMatrixStack().mulPose(new Quaternion(Vector3f.ZN, (float) (2f * MainPactMagic.aw * Math.PI), true));
    	    	        			event.getMatrixStack().scale(1f + (float) MainPactMagic.ay / 20, 1f + (float) MainPactMagic.ay / 20, 1f);
    	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
    	    	        			RenderSystem.disableBlend();
    	    	        			event.getMatrixStack().popPose();
    	        				}
    	        			} else if(MainPactMagic.control) {
    	        				event.getMatrixStack().pushPose();
	    	        			RenderSystem.enableBlend();
	    	    	            RenderSystem.defaultBlendFunc();
	    	        			minecraft.getTextureManager().bind(ANGEL_INDICATOR);
	            				event.getMatrixStack().translate(32, 32, 0);
	    	        			event.getMatrixStack().mulPose(new Quaternion(Vector3f.ZN, (float) (2f * MainPactMagic.aw * Math.PI), true));
	    	        			event.getMatrixStack().scale(1f + (float) MainPactMagic.ay / 20, 1f + (float) MainPactMagic.ay / 20, 1f);
	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
	    	        			RenderSystem.disableBlend();
	    	        			event.getMatrixStack().popPose();
    	        			}
    	        			
    	        			minecraft.getTextureManager().bind(pactMagic.getSlottedAngel().getTexture());
    	        			AbstractGui.blit(event.getMatrixStack(), 16 + (int) MainPactMagic.ax, 16 + (int) MainPactMagic.ay, 0, 0, 32, 32, 32, 32);
    	    			
        	    			float cooldown = (float) pactMagic.getSlottedAngel().getTimer() / (float) pactMagic.getSlottedAngel().getCooldown();
        	    			// TODO beautify
    	        			if(cooldown > 0.0F) {
        	    				RenderSystem.disableDepthTest();
        	    	            RenderSystem.disableTexture();
        	    	            RenderSystem.enableBlend();
        	    	            RenderSystem.defaultBlendFunc();
        	    	            Tessellator tessellator1 = Tessellator.getInstance();
        	    	            BufferBuilder bufferbuilder1 = tessellator1.getBuilder();
        	    	            MainPactMagic.draw(bufferbuilder1, 14, 14 + (int) (38 * (1f - cooldown)), 35, (int) (38 * cooldown), 255, 255, 255, 128);
        	    	            RenderSystem.enableTexture();
        	    	            RenderSystem.enableDepthTest();
        	    			}
    	        			
    	    				minecraft.getTextureManager().bind(BORDER);
    	        			AbstractGui.blit(event.getMatrixStack(), 8, 8, 0, 0, 48, 48, 48, 48);
    	    			}
    	    			
    	    			if(pactMagic.getSlottedDemon() != null) {
    	    				int modx = 0;
    	    				if(pactMagic.getSlottedAngel() == null) {
    	    					modx = -64;
    	    				}
    	    				if(!minecraft.isPaused()) {
    	    					MainPactMagic.dw += 0.05;
    	    				}
    	    				MainPactMagic.dx = (int) (Math.sin((MainPactMagic.dw * Math.PI) / 4) * 4);
    	    				MainPactMagic.dy = (int) (Math.sin((MainPactMagic.dw * Math.PI) / 2) * 2);
    	    				
    	    				if(MainPactMagic.alt) {
    	        				event.getMatrixStack().pushPose();
	    	        			RenderSystem.enableBlend();
	    	    	            RenderSystem.defaultBlendFunc();
	    	        			minecraft.getTextureManager().bind(DEMON_INDICATOR);
	            				event.getMatrixStack().translate(96 + modx, 32, 0);
	    	        			event.getMatrixStack().mulPose(new Quaternion(Vector3f.ZN, (float) (2f * MainPactMagic.dw * Math.PI), true));
	    	        			event.getMatrixStack().scale(1f + (float) MainPactMagic.dy / 20, 1f + (float) MainPactMagic.dy / 20, 1f);
	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
	    	        			RenderSystem.disableBlend();
	    	        			event.getMatrixStack().popPose();
    	        			}
    	    				
    	        			minecraft.getTextureManager().bind(pactMagic.getSlottedDemon().getTexture());
    	        			AbstractGui.blit(event.getMatrixStack(), 80 + (int) MainPactMagic.dx + modx, 16 + (int) MainPactMagic.dy, 0, 0, 32, 32, 32, 32);

        	    			minecraft.getTextureManager().bind(BORDER);
    	        			AbstractGui.blit(event.getMatrixStack(), 72 + modx, 8, 0, 0, 48, 48, 48, 48);
    	    			}
    				});
    			}
    			// TODO replace with more efficient code
    			
//    			RequestPlayerPacketHandler packet = new RequestPlayerPacketHandler();
//    			PacketHandlerPactMagic.INSTANCE.sendToServer(packet);
//    			packet.withPlayer(player -> {
//    				// code
//    			});
    			
    			// render karma bar
//    		} else if(event.getType() == ElementType.EXPERIENCE) {
//    	        Minecraft.getInstance().getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
//    	        Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), event.getWindow().getWidth() / 2 - 91, event.getWindow().getHeight() - 32 - 3 - 5, 0, 64, 182, 5);
    		}
    	}
    	@SubscribeEvent
    	@OnlyIn(Dist.CLIENT)
    	public static void onMouseInput(final InputEvent.MouseInputEvent event) {
    		boolean able = true; // not paused etc. // TODO
    		boolean rightClick = event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
    		boolean pressed = event.getAction() == GLFW.GLFW_PRESS;
    		boolean heldControl = (event.getMods() & GLFW.GLFW_MOD_CONTROL) != 0;
    		boolean heldAlt = (event.getMods() & GLFW.GLFW_MOD_ALT) != 0;
    		
    		if(able && rightClick && pressed) {
    			if(heldAlt) {
        			PacketHandlerPactMagic.INSTANCE.sendToServer(new SpellCastPacketHandler(true));
    			} else if(heldControl) {
        			PacketHandlerPactMagic.INSTANCE.sendToServer(new SpellCastPacketHandler(false));
    			}
    		}
    	}
    	@SubscribeEvent
    	@OnlyIn(Dist.CLIENT)
    	public static void onKeyInput(final InputEvent.KeyInputEvent event) {
    		if(!Minecraft.getInstance().isPaused() && event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() == GLFW.GLFW_PRESS) {
    			PacketHandlerPactMagic.INSTANCE.sendToServer(new JumpPacketHandler());
    		}
    		
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() == GLFW.GLFW_PRESS) {
    			MainPactMagic.control = true;
    		}
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() == GLFW.GLFW_RELEASE) {
    			MainPactMagic.control = false;
    		}
    		
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_PRESS) {
    			MainPactMagic.alt = true;
    		}
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_RELEASE) {
    			MainPactMagic.alt = false;
    		}
    	}
    	
    	@SubscribeEvent
    	public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
    		if(event.getObject() instanceof ServerPlayerEntity) {
    			event.addCapability(new ResourceLocation(MainPactMagic.MODID, event.getObject().getUUID().toString()), new PactMagicProvider(new PactMagic((ServerPlayerEntity) event.getObject())));
    			MainPactMagic.player = (ServerPlayerEntity) event.getObject();
    		}
    	}
    	@SubscribeEvent
    	public static void onPlayerTick(final PlayerTickEvent event) {
    		event.player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(IPactMagic::tick);
    	}
    	@SubscribeEvent
    	public static void onLivingDamage(final LivingDamageEvent event) {
    		if(event.getEntityLiving() instanceof ServerPlayerEntity) {
    			((ServerPlayerEntity) event.getEntityLiving()).getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
    				if(pactMagic.getSlottedAngel() == Angels.ANGELS && ((IToggleable) pactMagic.getSlottedAngel()).isOn()) {
    					if(!event.getSource().isBypassInvul()) {
        					event.setCanceled(true);
        					pactMagic.slotAngel(null);
    					}
    				}
    			});
    		}
    	}
		@SubscribeEvent
		public static void onBiomeLoad(final BiomeLoadingEvent biomeLoadingEvent) {
			final List<Supplier<StructureFeature<?, ?>>> structures = biomeLoadingEvent.getGeneration().getStructures();
			structures.add(() -> StructureFeaturesPactMagic.ANGEL_TEMPLE.get().configured(NoFeatureConfig.INSTANCE));
		}
    }
    
    // From ItemRenderer
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.vertex((double)(x + 0), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.vertex((double)(x + 0), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.vertex((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.vertex((double)(x + width), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().end();
     }
    public static double quad(double x, double xint2, double max, int exp, boolean clamp) {
		double v = -max * Math.pow(((2 * x - xint2) / 2) / (xint2 / 2), exp) + max;
		if(clamp) {
        	return MathHelper.clamp(v, 0 < max ? 0 : max, max > 0 ? max : 0);
    	} else {
        	return v;
    	}
    }
}