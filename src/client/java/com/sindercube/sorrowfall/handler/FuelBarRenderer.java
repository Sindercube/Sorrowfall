package com.sindercube.sorrowfall.handler;

import com.sindercube.sorrowfall.content.item.HellgateItem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class FuelBarRenderer {

	public static void drawFuelBar(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
		if (!(stack.getItem() instanceof HellgateItem hellgate)) return;

		int color = hellgate.getFuelColor(stack);
		int step = hellgate.getFuelStep(stack);
		int barX = x + 6;
		int barY = y + 13;
//		this.fill(RenderLayer.getGuiOverlay(), barX, barY, barX + 13, barY + 2, -16777216);
//		this.fill(RenderLayer.getGuiOverlay(), barX, barY, barX + color, barY + 1, step | -16777216);
	}

}
