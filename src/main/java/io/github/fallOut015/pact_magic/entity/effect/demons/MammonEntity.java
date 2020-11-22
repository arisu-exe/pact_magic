package io.github.fallOut015.pact_magic.entity.effect.demons;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.entity.EntityTypePactMagic;
import io.github.fallOut015.pact_magic.entity.effect.SigilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class MammonEntity extends Entity {
	@Nullable ServerPlayerEntity caster;
	static final DataParameter<Boolean> CASTING = EntityDataManager.createKey(MammonEntity.class, DataSerializers.BOOLEAN);
	static final DataParameter<Integer> ANIMATION_FRAMES = EntityDataManager.createKey(MammonEntity.class, DataSerializers.VARINT);
	@Nullable SigilEntity[] sigils;
	float headYawRotation;
	
	public MammonEntity(EntityType<? extends MammonEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		
		this.noClip = false;
		this.sigils = new SigilEntity[] {
			null, null, null, null, null, null, null, null
		};
	}
	
	public void setCaster(ServerPlayerEntity caster) {
		this.caster = caster;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.isCasting()) {
			this.dataManager.set(ANIMATION_FRAMES, this.getAnimationFrames() + 1);
			
			boolean allnull = true;
			for(int i = 0; i < this.sigils.length; ++ i) {
				SigilEntity sigil = this.sigils[i];
				if(sigil != null && !sigil.isAlive()) {
					this.sigils[i] = null;
				} else {
					allnull = false;
				}
			}
			if(allnull) {
				this.stopCasting();
				this.dataManager.set(ANIMATION_FRAMES, 0);
			}
		}
		
		if(this.caster != null) {
//			if(this.getDistance(this.caster) > 1.414) {
				if(!this.isCasting()) {
					this.setVelocity(10, 10, 10);
					this.markVelocityChanged();
				}
//				this.setMotion(this.caster.getPositionVec().subtract(this.getPositionVec()));
//			}
			
			if(this.isCasting()) {
				this.setRotationYawHead(0);
			} else {
				double dx = this.getPosX() - this.caster.getPosX();
				double dy = this.getPosZ() - this.caster.getPosZ();
				double d = (float) (MathHelper.atan2(dy, dx) * (double) (180F / (float) Math.PI)) + 90.0F;

				float f = MathHelper.wrapSubtractDegrees(this.getRotationYawHead(), (float) d);
				float f1 = MathHelper.clamp(f, -10f, 10f);
				
				this.setRotationYawHead(MathHelper.lerp(0.5f, this.getRotationYawHead(), this.getRotationYawHead() + f1));
			}
		}
	}
	
	public void effect() {
		if(!this.isCasting() && this.caster != null) {
			this.startCasting();
			
			if(this.getEntityWorld() instanceof ServerWorld) {
				SigilEntity sigil = EntityTypePactMagic.SIGIL.get().spawn((ServerWorld) this.getEntityWorld(), null, null, this.caster, this.getPosition(), SpawnReason.MOB_SUMMONED, false, false);
				sigil.setMaxLife(50);
				sigil.setIsEffect();
				this.getEntityWorld().addEntity(sigil);
				
				for(int i = 0; i < 8; ++ i) {
					double x = MathHelper.cos((float) Math.toRadians(i * 45)) * this.rand.nextFloat() * 8 + this.getPosX();
					double z = MathHelper.sin((float) Math.toRadians(i * 45)) * this.rand.nextFloat() * 8 + this.getPosZ();
					double y = this.getEntityWorld().getHeight(Type.WORLD_SURFACE, (int) x, (int) z);
					SigilEntity sigil2 = EntityTypePactMagic.SIGIL.get().spawn((ServerWorld) this.getEntityWorld(), null, null, this.caster, new BlockPos(x, y, z), SpawnReason.MOB_SUMMONED, false, false);
					sigil2.setMaxLife(400);
					sigil2.setCaster(this.caster);
					this.getEntityWorld().addEntity(sigil2);
					if(sigils[i] == null) {
						sigils[i] = sigil2;
					}
				}
			}
		}
	}
	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		
		for(float i = 0; i < 360; i += 22.5) {
			this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY() + this.getHeight(), this.getPosZ(), MathHelper.cos((float) Math.toRadians(i)) / 10f, 0, MathHelper.sin((float) Math.toRadians(i)) / 10f);
		}
	}
	@Override
	public float getRotationYawHead() {
		return this.headYawRotation;
	}
	@Override
	public void setRotationYawHead(float rotation) {
		this.headYawRotation = rotation;
	}

	void startCasting() {
		this.dataManager.set(CASTING, true);
	}
	void stopCasting() {
		this.dataManager.set(CASTING, false);
	}
	public boolean isCasting() {
		return this.dataManager.get(CASTING).booleanValue();
	}
	
	public int getAnimationFrames() {
		return this.dataManager.get(ANIMATION_FRAMES).intValue();
	}
	
	@Override
	protected void registerData() {
		this.dataManager.register(CASTING, false);
		this.dataManager.register(ANIMATION_FRAMES, 0);
	}
	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.dataManager.set(CASTING, compound.getBoolean("CASTING"));
		this.dataManager.set(ANIMATION_FRAMES, compound.getInt("ANIMATION_FRAMES"));
		
		if(this.world.getPlayerByUuid(compound.getUniqueId("caster")) != null) {
			this.caster = (ServerPlayerEntity) this.world.getPlayerByUuid(compound.getUniqueId("caster"));
		}
	}
	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putBoolean("CASTING", this.isCasting());
		compound.putInt("ANIMATION_FRAMES", this.getAnimationFrames());
		
		if(this.caster != null) {
			compound.putUniqueId("caster", this.caster.getUniqueID());
		}
	}
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}