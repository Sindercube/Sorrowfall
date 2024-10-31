package com.sindercube.sorrowfall.mixin;

import com.sindercube.sorrowfall.registry.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DragonFireballEntity.class)
public class DragonFireballEntityMixin {

	@ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;<init>(Lnet/minecraft/registry/entry/RegistryEntry;II)V"))
	private RegistryEntry<StatusEffect> injected(RegistryEntry<StatusEffect> effect) {
		return ModStatusEffects.INSTANT_DRAGON_FIRE;
	}

}
