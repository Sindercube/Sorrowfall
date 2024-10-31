package com.sindercube.sorrowfall;

import com.sindercube.sorrowfall.registry.ModBlocks;
import com.sindercube.sorrowfall.registry.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class HellgateClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.THIN_ICE, RenderLayer.getTranslucent());
		ModEntityRenderers.init();
	}

}
