package com.sindercube.sorrowfall.registry;

import com.sindercube.sorrowfall.Sorrowfall;
import com.sindercube.sorrowfall.content.block.ThinIceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {

	public static void init() {}

	public static final Block THIN_ICE = register("thin_ice", new ThinIceBlock(
		Blocks.FROSTED_ICE.getSettings().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never).breakInstantly()
	));

	public static Block register(String path, Block block) {
		return Registry.register(Registries.BLOCK, Sorrowfall.of(path), block);
	}

}
