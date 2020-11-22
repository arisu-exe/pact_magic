package io.github.fallOut015.pact_magic.entity.effect;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.Main;
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
	static final DataParameter<Integer> MAX_LIFE = EntityDataManager.createKey(SigilEntity.class, DataSerializers.VARINT);
	static final DataParameter<Boolean> EFFECT = EntityDataManager.createKey(SigilEntity.class, DataSerializers.BOOLEAN);
	final Predicate<? super Entity> snareable;

	public SigilEntity(EntityType<? extends SigilEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		
		this.snareable = entity -> entity.isAlive() && entity.isLiving() && entity != this.caster;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.getMaxLife() != 0 && this.ticksExisted >= this.getMaxLife()) {
			if(!this.world.isRemote) {
				this.setDead();
			}
		}
		
		if(this.world.isRemote && this.ticksExisted % 4 == 0) {
			double x = (this.rand.nextInt(3) - 1) * this.scale(this.ticksExisted, this.isEffect() ? 1 : 0.5, 4) + this.getPosX();
			double z = (this.rand.nextInt(3) - 1) * this.scale(this.ticksExisted, this.isEffect() ? 1 : 0.5, 4) + this.getPosZ();
			this.world.addParticle(ParticleTypes.FLAME, x, this.getPosY(), z, 0, 0.05f, 0);
		}
		
		if(!this.isEffect()) {
			// TODO
			// Fire? Wither damage? Vines? Mammon physically grabbing the entity?
			this.world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPositionVec().add(-1, 0, -1), this.getPositionVec().add(1, 2, 1)), this.snareable).forEach(entity -> {
				entity.setFire(5);
			});
		}
	}

	@Override
	protected void registerData() {
		this.dataManager.register(MAX_LIFE, 0);
		this.dataManager.register(EFFECT, false);
	}
	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.dataManager.set(MAX_LIFE, compound.getInt("MAX_LIFE"));
		this.dataManager.set(EFFECT, compound.getBoolean("EFFECT"));
	}
	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("MAX_LIFE", this.getMaxLife());
		compound.putBoolean("EFFECT", this.isEffect());
	}
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public double scale(double x, double max, int exp) {
        return Main.quad(x, this.getMaxLife(), max, exp, true);
	}
	public void setMaxLife(int value) {
		this.dataManager.set(MAX_LIFE, value);
	}
	int getMaxLife() {
		return this.dataManager.get(MAX_LIFE);
	}
	public boolean isEffect() {
		return this.dataManager.get(EFFECT);
	}
	public void setIsEffect() {
		this.dataManager.set(EFFECT, true);
	}
	public void setCaster(ServerPlayerEntity caster) {
		this.caster = caster;
	}
}