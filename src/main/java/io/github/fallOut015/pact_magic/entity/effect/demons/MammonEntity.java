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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class MammonEntity extends Entity {
	@Nullable ServerPlayerEntity caster;

	public MammonEntity(EntityType<? extends MammonEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}
	
	public void setCaster(ServerPlayerEntity caster) {
		this.caster = caster;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.caster != null) {
			this.setPosition(this.caster.getPosX(), this.caster.getPosY(), this.caster.getPosZ());
		}
		
		this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), 0, 0, 0);
	}
	
	public void effect() {
		// TODO Auto-generated method stub
		// Animate by moving into the ground (clipping)
		
		if(this.getEntityWorld() instanceof ServerWorld) {
			SigilEntity sigil = EntityTypePactMagic.SIGIL.get().spawn((ServerWorld) this.getEntityWorld(), null, null, this.caster, this.getPosition(), SpawnReason.MOB_SUMMONED, false, false);
			this.getEntityWorld().addEntity(sigil);
		}
	}

	@Override
	protected void registerData() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void readAdditional(CompoundNBT compound) {
		// TODO Auto-generated method stub
	}
	@Override
	protected void writeAdditional(CompoundNBT compound) {
		// TODO Auto-generated method stub
	}
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}