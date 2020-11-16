package io.github.fallOut015.pact_magic.entity.effect.angels;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SeraphEntity extends Entity {
	@Nullable ServerPlayerEntity caster;
	public static final DataParameter<Boolean> SHIELDING = EntityDataManager.createKey(SeraphEntity.class, DataSerializers.BOOLEAN);
	
	public SeraphEntity(EntityType<? extends SeraphEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.caster != null) {
////			this.move(MoverType.SELF, this.caster.getPositionVec());
//			this.setPosition(this.caster.getPosX(), this.caster.getPosY(), this.caster.getPosZ());
			
			if(this.world.isRemote/* && this.isShielding()*/) {
				double x;
				double y;
				double z;
				double rotation = this.ticksExisted * 2;
				double size = 3;
				
				for(int i = 0; i < 360; i += 18) {
					x = size * Math.cos(Math.toRadians(i + rotation)) + this.caster.getPosX();
					y = size * Math.sin(Math.toRadians(i + rotation)) + this.caster.getPosY();
					z = size * Math.sin(Math.toRadians(i)) + this.caster.getPosZ();
					
					this.world.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);
				}
				for(int i = 0; i < 360; i += 18) {
					x = size * Math.cos(Math.toRadians(i + rotation)) + this.caster.getPosX();
					y = size * Math.sin(Math.toRadians(i)) + this.caster.getPosY();
					z = size * Math.sin(Math.toRadians(i + rotation)) + this.caster.getPosZ();
					
					this.world.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);
				}
				for(int i = 0; i < 360; i += 18) {
					x = size * Math.sin(Math.toRadians(i)) + this.caster.getPosX();
					y = size * Math.sin(Math.toRadians(i + rotation)) + this.caster.getPosY();
					z = size * Math.cos(Math.toRadians(i + rotation)) + this.caster.getPosZ();
					
					this.world.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	protected void registerData() {
		this.dataManager.register(SHIELDING, false);
	}
	@Override
	protected void readAdditional(CompoundNBT compound) {
		compound.putBoolean("SHIELDING", this.isShielding());
	}
	@Override
	protected void writeAdditional(CompoundNBT compound) {
		this.setShielding(compound.getBoolean("SHIELDING"));
	}
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public boolean isShielding() {
		return this.dataManager.get(SHIELDING);
	}
	public void setShielding(boolean shielding) {
		this.dataManager.set(SHIELDING, shielding);
	}
	public void setCaster(ServerPlayerEntity caster) {
		this.caster = caster;
	}
}