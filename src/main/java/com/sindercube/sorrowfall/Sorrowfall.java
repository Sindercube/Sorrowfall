package com.sindercube.sorrowfall;

import com.sindercube.sorrowfall.registry.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sorrowfall implements ModInitializer {

	public static final String MOD_ID = "sorrowfall";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String path) {
		return Identifier.of(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ModEnchantmentEffectTypes.init();
		ModComponents.init();
		ModItems.init();
		ModBlocks.init();
		ModStatusEffects.init();
		ModEntityTypes.init();
	}

}
