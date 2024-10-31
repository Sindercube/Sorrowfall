package com.sindercube.sorrowfall.registry;

import com.sindercube.sorrowfall.Sorrowfall;
import com.sindercube.sorrowfall.content.effect.InstantDragonBreathStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModStatusEffects {

	public static void init() {}

	public static final RegistryEntry<StatusEffect> INSTANT_DRAGON_FIRE = registerReference("instant_dragon_fire", new InstantDragonBreathStatusEffect(StatusEffectCategory.HARMFUL, 11101546));

	public static RegistryEntry<StatusEffect> registerReference(String path, StatusEffect effect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, Sorrowfall.of(path), effect);
	}

}
