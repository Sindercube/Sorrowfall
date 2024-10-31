package com.sindercube.sorrowfall.content.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantHealthOrDamageStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public class InstantDragonBreathStatusEffect extends InstantHealthOrDamageStatusEffect {

	public InstantDragonBreathStatusEffect(StatusEffectCategory category, int color) {
		super(category, color, true);
	}

	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		entity.damage(entity.getDamageSources().dragonBreath(), (float)(6 << amplifier));
		return true;
	}

	public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
		int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
		target.damage(target.getDamageSources().dragonBreath(), (float)i);
	}

}
