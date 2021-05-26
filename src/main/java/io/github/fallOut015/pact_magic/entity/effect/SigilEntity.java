package io.github.fallOut015.pact_magic.entity.effect;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SigilEntity extends Entity {
	@Nullable ServerPlayerEntity caster;
	static final DataParameter<Integer> MAX_LIFE = EntityDataManager.defineId(SigilEntity.class, DataSerializers.INT);
	static final DataParameter<Boolean> EFFECT = EntityDataManager.defineId(SigilEntity.class, DataSerializers.BOOLEAN);
	final Predicate<? super Entity> snareable;

	public SigilEntity(EntityType<? extends SigilEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		
		this.snareable = entity -> entity.isAlive() && entity.showVehicleHealth() && entity != this.caster;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.getMaxLife() != 0 && this.tickCount >= this.getMaxLife()) {
			if(!this.level.isClientSide) {
				this.removeAfterChangingDimensions();
			}
		}
		
		if(this.level.isClientSide && this.tickCount % 4 == 0) {
			double x = (this.random.nextInt(3) - 1) * this.scale(this.tickCount, this.isEffect() ? 1 : 0.5, 4) + this.getX();
			double z = (this.random.nextInt(3) - 1) * this.scale(this.tickCount, this.isEffect() ? 1 : 0.5, 4) + this.getZ();
			this.level.addParticle(ParticleTypes.FLAME, x, this.getY(), z, 0, 0.05f, 0);
		}
		
		if(!this.isEffect()) {
			// TODO
			// Fire? Wither damage? Vines? Mammon physically grabbing the entity?
			this.level.getEntities(this, new AxisAlignedBB(this.position().add(-1, 0, -1), this.position().add(1, 2, 1)), this.snareable).forEach(entity -> {
				entity.setSecondsOnFire(5);
			});
		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(MAX_LIFE, 0);
		this.entityData.define(EFFECT, false);
	}
	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {
		this.entityData.set(MAX_LIFE, compound.getInt("MAX_LIFE"));
		this.entityData.set(EFFECT, compound.getBoolean("EFFECT"));
	}
	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {
		compound.putInt("MAX_LIFE", this.getMaxLife());
		compound.putBoolean("EFFECT", this.isEffect());
	}
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public double scale(double x, double max, int exp) {
        return MainPactMagic.quad(x, this.getMaxLife(), max, exp, true);
	}
	public void setMaxLife(int value) {
		this.entityData.set(MAX_LIFE, value);
	}
	int getMaxLife() {
		return this.entityData.get(MAX_LIFE);
	}
	public boolean isEffect() {
		return this.entityData.get(EFFECT);
	}
	public void setIsEffect() {
		this.entityData.set(EFFECT, true);
	}
	public void setCaster(ServerPlayerEntity caster) {
		this.caster = caster;
	}
}