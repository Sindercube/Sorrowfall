package com.sindercube.sorrowfall.registry;

import com.mojang.serialization.Codec;
import com.sindercube.sorrowfall.Sorrowfall;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.List;

public class ModEnchantmentEffectTypes {

	public static void init() {}

	public static final ComponentType<Unit> HELLGATE_DRAGON_FIREBALL = register("hellgate_dragon_fireball", Unit.CODEC);
	public static final ComponentType<Unit> HELLGATE_SOUL_FIRE_ATTACK = register("hellgate_soul_fire_attack", Unit.CODEC);
	public static final ComponentType<EnchantmentValueEffect> HELLGATE_MAX_FUEL = register("hellgate_max_fuel", EnchantmentValueEffect.CODEC);

	private static <T> ComponentType<T> register(String path, Codec<T> codec) {
		ComponentType<T> type = ComponentType.<T>builder()
			.codec(codec)
			.packetCodec(PacketCodecs.codec(codec))
			.build();
		return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Sorrowfall.of(path), type);
	}

}
