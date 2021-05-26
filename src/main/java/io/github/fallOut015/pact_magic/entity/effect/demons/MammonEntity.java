package io.github.fallOut015.pact_magic.entity.effect.demons;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.entity.EntitiesPactMagic;
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
	static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(MammonEntity.class, DataSerializers.BOOLEAN);
	static final DataParameter<Integer> ANIMATION_FRAMES = EntityDataManager.defineId(MammonEntity.class, DataSerializers.INT);
	@Nullable SigilEntity[] sigils;
	float headYawRotation;
	
	public MammonEntity(EntityType<? extends MammonEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		
		this.noPhysics = false;
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
			this.entityData.set(ANIMATION_FRAMES, this.getAnimationFrames() + 1);
			
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
				this.entityData.set(ANIMATION_FRAMES, 0);
			}
		}
		
		if(this.caster != null) {
//			if(this.getDistance(this.caster) > 1.414) {
				if(!this.isCasting()) {
					this.lerpMotion(10, 10, 10);
					this.markHurt();
				}
//				this.setMotion(this.caster.getPositionVec().subtract(this.getPositionVec()));
//			}
			
			if(this.isCasting()) {
				this.setYHeadRot(0);
			} else {
				double dx = this.getX() - this.caster.getX();
				double dy = this.getZ() - this.caster.getZ();
				double d = (float) (MathHelper.atan2(dy, dx) * (double) (180F / (float) Math.PI)) + 90.0F;

				float f = MathHelper.degreesDifference(this.getYHeadRot(), (float) d);
				float f1 = MathHelper.clamp(f, -10f, 10f);
				
				this.setYHeadRot(MathHelper.lerp(0.5f, this.getYHeadRot(), this.getYHeadRot() + f1));
			}
		}
	}
	
	public void effect() {
		if(!this.isCasting() && this.caster != null) {
			this.startCasting();
			
			if(this.getCommandSenderWorld() instanceof ServerWorld) {
				SigilEntity sigil = EntitiesPactMagic.SIGIL.get().spawn((ServerWorld) this.getCommandSenderWorld(), null, null, this.caster, this.blockPosition(), SpawnReason.MOB_SUMMONED, false, false);
				sigil.setMaxLife(50);
				sigil.setIsEffect();
				this.getCommandSenderWorld().addFreshEntity(sigil);
				
				for(int i = 0; i < 8; ++ i) {
					double x = MathHelper.cos((float) Math.toRadians(i * 45)) * this.random.nextFloat() * 8 + this.getX();
					double z = MathHelper.sin((float) Math.toRadians(i * 45)) * this.random.nextFloat() * 8 + this.getZ();
					double y = this.getCommandSenderWorld().getHeight(Type.WORLD_SURFACE, (int) x, (int) z);
					SigilEntity sigil2 = EntitiesPactMagic.SIGIL.get().spawn((ServerWorld) this.getCommandSenderWorld(), null, null, this.caster, new BlockPos(x, y, z), SpawnReason.MOB_SUMMONED, false, false);
					sigil2.setMaxLife(400);
					sigil2.setCaster(this.caster);
					this.getCommandSenderWorld().addFreshEntity(sigil2);
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
			this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + this.getBbHeight(), this.getZ(), MathHelper.cos((float) Math.toRadians(i)) / 10f, 0, MathHelper.sin((float) Math.toRadians(i)) / 10f);
		}
	}
	@Override
	public float getYHeadRot() {
		return this.headYawRotation;
	}
	@Override
	public void setYHeadRot(float rotation) {
		this.headYawRotation = rotation;
	}

	void startCasting() {
		this.entityData.set(CASTING, true);
	}
	void stopCasting() {
		this.entityData.set(CASTING, false);
	}
	public boolean isCasting() {
		return this.entityData.get(CASTING).booleanValue();
	}
	
	public int getAnimationFrames() {
		return this.entityData.get(ANIMATION_FRAMES).intValue();
	}
	
	@Override
	protected void defineSynchedData() {
		this.entityData.define(CASTING, false);
		this.entityData.define(ANIMATION_FRAMES, 0);
	}
	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {
		this.entityData.set(CASTING, compound.getBoolean("CASTING"));
		this.entityData.set(ANIMATION_FRAMES, compound.getInt("ANIMATION_FRAMES"));
		
		if(this.level.getPlayerByUUID(compound.getUUID("caster")) != null) {
			this.caster = (ServerPlayerEntity) this.level.getPlayerByUUID(compound.getUUID("caster"));
		}
	}
	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {
		compound.putBoolean("CASTING", this.isCasting());
		compound.putInt("ANIMATION_FRAMES", this.getAnimationFrames());
		
		if(this.caster != null) {
			compound.putUUID("caster", this.caster.getUUID());
		}
	}
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}