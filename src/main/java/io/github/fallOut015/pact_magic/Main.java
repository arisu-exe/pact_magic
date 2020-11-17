package io.github.fallOut015.pact_magic;

import javax.annotation.Nullable;

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
import io.github.fallOut015.pact_magic.entity.EntityTypePactMagic;
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

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "pact_magic";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ResourceLocation BORDER = new ResourceLocation("pact_magic", "textures/gui/border.png");
    public static final ResourceLocation ANGEL_INDICATOR = new ResourceLocation("pact_magic", "textures/gui/angel_indicator.png");
    public static final ResourceLocation DEMON_INDICATOR = new ResourceLocation("pact_magic", "textures/gui/demon_indicator.png");
    
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
    	Main.player = null;
    	Main.aw = 0;
    	Main.ax = 0;
    	Main.ay = 0;
    	Main.dw = 0;
    	Main.dx = 0;
    	Main.dy = 0;
    	Main.control = false;
    	Main.alt = false;
    }
    
    public Main() {
    	ItemsPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	EntityTypePactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	RecipeSerializersPactMagic.register(FMLJavaModLoadingContext.get().getModEventBus());
    	
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
    	PacketHandlerPactMagic.setup(event);
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
    			if(Main.player != null) {
    				Main.player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
    	    			Minecraft minecraft = Minecraft.getInstance();
    	    			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
    	    			if(pactMagic.getSlottedAngel() != null) {
    	        			if(pactMagic.getSlottedAngel().isPrepared()) {
    	        				if(!minecraft.isGamePaused()) {
    	        					Main.aw += 0.05;    	        					
    	        				}
        	        			Main.ax = (Math.sin((Main.aw * Math.PI) / 4) * 4);
        	        			Main.ay = (Math.sin((Main.aw * Math.PI) / 2) * 2);
    	        			} else {
    	        				Main.aw = 0;
    	        				Main.ax = 0;
    	        				Main.ay = 0;
    	        			}
    	        			
    	        			if(pactMagic.getSlottedAngel() instanceof IToggleable) {
    	        				if(((IToggleable) pactMagic.getSlottedAngel()).isOn()) {
    	        					event.getMatrixStack().push();
    	    	        			RenderSystem.enableBlend();
    	    	    	            RenderSystem.defaultBlendFunc();
    	    	        			minecraft.getTextureManager().bindTexture(ANGEL_INDICATOR);
    	            				event.getMatrixStack().translate(32, 32, 0);
    	    	        			event.getMatrixStack().rotate(new Quaternion(Vector3f.ZN, (float) (2f * Main.aw * Math.PI), true));
    	    	        			event.getMatrixStack().scale(1f + (float) Main.ay / 20, 1f + (float) Main.ay / 20, 1f);
    	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
    	    	        			RenderSystem.disableBlend();
    	    	        			event.getMatrixStack().pop();
    	        				}
    	        			} else if(Main.control) {
    	        				event.getMatrixStack().push();
	    	        			RenderSystem.enableBlend();
	    	    	            RenderSystem.defaultBlendFunc();
	    	        			minecraft.getTextureManager().bindTexture(ANGEL_INDICATOR);
	            				event.getMatrixStack().translate(32, 32, 0);
	    	        			event.getMatrixStack().rotate(new Quaternion(Vector3f.ZN, (float) (2f * Main.aw * Math.PI), true));
	    	        			event.getMatrixStack().scale(1f + (float) Main.ay / 20, 1f + (float) Main.ay / 20, 1f);
	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
	    	        			RenderSystem.disableBlend();
	    	        			event.getMatrixStack().pop();
    	        			}
    	        			
    	        			minecraft.getTextureManager().bindTexture(new ResourceLocation("pact_magic", "textures/gui/angels/" + pactMagic.getSlottedAngel().getID() + ".png"));
    	        			AbstractGui.blit(event.getMatrixStack(), 16 + (int) Main.ax, 16 + (int) Main.ay, 0, 0, 32, 32, 32, 32);
    	    			
        	    			float cooldown = (float) pactMagic.getSlottedAngel().getTimer() / (float) pactMagic.getSlottedAngel().getCooldown();
        	    			// TODO beautify
    	        			if(cooldown > 0.0F) {
        	    				RenderSystem.disableDepthTest();
        	    	            RenderSystem.disableTexture();
        	    	            RenderSystem.enableBlend();
        	    	            RenderSystem.defaultBlendFunc();
        	    	            Tessellator tessellator1 = Tessellator.getInstance();
        	    	            BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
        	    	            Main.draw(bufferbuilder1, 14, 14 + (int) (38 * (1f - cooldown)), 35, (int) (38 * cooldown), 255, 255, 255, 128);
        	    	            RenderSystem.enableTexture();
        	    	            RenderSystem.enableDepthTest();
        	    			}
    	        			
    	    				minecraft.getTextureManager().bindTexture(BORDER);
    	        			AbstractGui.blit(event.getMatrixStack(), 8, 8, 0, 0, 48, 48, 48, 48);
    	    			}
    	    			
    	    			if(pactMagic.getSlottedDemon() != null) {
    	    				int modx = 0;
    	    				if(pactMagic.getSlottedAngel() == null) {
    	    					modx = -64;
    	    				}
    	    				if(!minecraft.isGamePaused()) {
    	    					Main.dw += 0.05;    	        					
    	    				}
    	    				Main.dx = (int) (Math.sin((Main.dw * Math.PI) / 4) * 4);
    	    				Main.dy = (int) (Math.sin((Main.dw * Math.PI) / 2) * 2);
    	    				
    	    				if(Main.alt) {
    	        				event.getMatrixStack().push();
	    	        			RenderSystem.enableBlend();
	    	    	            RenderSystem.defaultBlendFunc();
	    	        			minecraft.getTextureManager().bindTexture(DEMON_INDICATOR);
	            				event.getMatrixStack().translate(96 + modx, 32, 0);
	    	        			event.getMatrixStack().rotate(new Quaternion(Vector3f.ZN, (float) (2f * Main.dw * Math.PI), true));
	    	        			event.getMatrixStack().scale(1f + (float) Main.dy / 20, 1f + (float) Main.dy / 20, 1f);
	    	        			AbstractGui.blit(event.getMatrixStack(), -20, -20, 0, 0, 40, 40, 40, 40);
	    	        			RenderSystem.disableBlend();
	    	        			event.getMatrixStack().pop();
    	        			}
    	    				
    	        			minecraft.getTextureManager().bindTexture(new ResourceLocation("pact_magic", "textures/gui/demons/" + pactMagic.getSlottedDemon().getID() + ".png"));
    	        			AbstractGui.blit(event.getMatrixStack(), 80 + (int) Main.dx + modx, 16 + (int) Main.dy, 0, 0, 32, 32, 32, 32);

        	    			minecraft.getTextureManager().bindTexture(BORDER);
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
    		if(!Minecraft.getInstance().isGamePaused() && event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() == GLFW.GLFW_PRESS) {
    			PacketHandlerPactMagic.INSTANCE.sendToServer(new JumpPacketHandler());
    		}
    		
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() == GLFW.GLFW_PRESS) {
    			Main.control = true;
    		}
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() == GLFW.GLFW_RELEASE) {
    			Main.control = false;
    		}
    		
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_PRESS) {
    			Main.alt = true;
    		}
    		if(event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_RELEASE) {
    			Main.alt = false;
    		}
    	}
    	@SubscribeEvent
    	public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
    		if(event.getObject() instanceof ServerPlayerEntity) {
    			event.addCapability(new ResourceLocation("pact_magic", event.getObject().getUniqueID().toString()), new PactMagicProvider(new PactMagic((ServerPlayerEntity) event.getObject())));
    			Main.player = (ServerPlayerEntity) event.getObject();
    		}
    	}
    	@SubscribeEvent
    	public static void onPlayerTick(final PlayerTickEvent event) {
    		event.player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> pactMagic.tick());
    	}
    	@SubscribeEvent
    	public static void onLivingDamage(final LivingDamageEvent event) {
    		if(event.getEntityLiving() instanceof ServerPlayerEntity) {
    			((ServerPlayerEntity) event.getEntityLiving()).getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
    				if(pactMagic.getSlottedAngel() == Angels.ANGELS && ((IToggleable) pactMagic.getSlottedAngel()).isOn()) {
    					if(!event.getSource().canHarmInCreative()) {
        					event.setCanceled(true);
        					pactMagic.slotAngel(null);
    					}
    				}
    			});
    		}
    	}
    }
    
    // From ItemRenderer
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x + 0), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + 0), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
     }
}