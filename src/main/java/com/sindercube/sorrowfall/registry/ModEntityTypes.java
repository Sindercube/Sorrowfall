package com.sindercube.sorrowfall.registry;

import com.sindercube.sorrowfall.Sorrowfall;
import com.sindercube.sorrowfall.content.entity.WeakDragonFireballEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityTypes {

	public static void init() {}

	public static final EntityType<WeakDragonFireballEntity> WEAK_DRAGON_FIREBALL = register("weak_dragon_fireball",
		EntityType.Builder.<WeakDragonFireballEntity>create(WeakDragonFireballEntity::new, SpawnGroup.MISC)
			.dimensions(1.0F, 1.0F)
			.maxTrackingRange(4)
			.trackingTickInterval(10)
	);

	public static <T extends Entity> EntityType<T> register(String path, EntityType.Builder<T> builder) {
		return Registry.register(Registries.ENTITY_TYPE, Sorrowfall.of(path), builder.build(path));
	}

}
