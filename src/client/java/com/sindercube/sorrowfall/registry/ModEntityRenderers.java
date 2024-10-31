package com.sindercube.sorrowfall.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.DragonFireballEntityRenderer;

public class ModEntityRenderers {

	public static void init() {
		EntityRendererRegistry.register(ModEntityTypes.WEAK_DRAGON_FIREBALL, DragonFireballEntityRenderer::new);
	}

}
