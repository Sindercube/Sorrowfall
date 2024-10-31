package com.sindercube.sorrowfall.registry;

import com.sindercube.sorrowfall.Sorrowfall;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

	public static final SoundEvent ITEM_HELLGATE_FAIL = register("item.sorrowfall.hellgate.fail");
	public static final SoundEvent ITEM_HELLGATE_USE = register("item.sorrowfall.hellgate.use");

	private static SoundEvent register(String path) {
		Identifier id = Sorrowfall.of(path);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

}
