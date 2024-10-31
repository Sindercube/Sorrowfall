package com.sindercube.sorrowfall;

import com.sindercube.sorrowfall.registry.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class HellgateClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModEntityRenderers.init();
	}

}
