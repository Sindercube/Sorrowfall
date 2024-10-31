package com.sindercube.sorrowfall.content.entity;

import com.sindercube.sorrowfall.registry.ModEntityTypes;
import com.sindercube.sorrowfall.registry.ModStatusEffects;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WeakDragonFireballEntity extends DragonFireballEntity {

	public WeakDragonFireballEntity(EntityType<WeakDragonFireballEntity> type, World world) {
		super(type, world);
	}

	public WeakDragonFireballEntity(World world, LivingEntity owner, Vec3d velocity) {
		this(ModEntityTypes.WEAK_DRAGON_FIREBALL, world);
		this.refreshPositionAndAngles(owner.getX(), owner.getY(), owner.getZ(), this.getYaw(), this.getPitch());
		this.refreshPosition();
		this.setVelocityWithAcceleration(velocity, this.accelerationPower);
		this.setOwner(owner);
		this.setRotation(owner.getYaw(), owner.getPitch());
	}

	//	@Override
	//	public boolean damage(DamageSource source, float amount) {
	//		return !this.isInvulnerableTo(source);
	//	}

	@Override
	protected void onCollision(HitResult result) {
		HitResult.Type type = result.getType();
		if (type == HitResult.Type.ENTITY) {
			EntityHitResult entityResult = (EntityHitResult)result;
			this.onEntityHit(entityResult);
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, result.getPos(), GameEvent.Emitter.of(this, null));
		} else if (type == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult)result;
			this.onBlockHit(blockHitResult);
			BlockPos blockPos = blockHitResult.getBlockPos();
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
		}

		if (this.getWorld().isClient) return;
		if (result.getType() == HitResult.Type.ENTITY && this.isOwner(((EntityHitResult)result).getEntity())) return;

		AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
		cloud.setOwner((LivingEntity)this.getOwner());
		cloud.setParticleType(getParticleType());
		cloud.setRadius(1.5f);
		cloud.setDuration(100);
		cloud.setRadiusGrowth((4 - cloud.getRadius()) / (float)cloud.getDuration());
		cloud.addEffect(new StatusEffectInstance(ModStatusEffects.INSTANT_DRAGON_FIRE, 1, 1));

		this.getWorld().syncWorldEvent(2006, this.getBlockPos(), this.isSilent() ? -1 : 1);
		this.getWorld().spawnEntity(cloud);
		this.discard();
	}

}
