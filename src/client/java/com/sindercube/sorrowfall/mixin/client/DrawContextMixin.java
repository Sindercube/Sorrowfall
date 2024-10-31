package com.sindercube.sorrowfall.mixin.client;

import com.sindercube.sorrowfall.content.item.HellgateItem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin {

	@Shadow public void fill(RenderLayer guiOverlay, int k, int l, int i, int i1, int i2) {}

	@Inject(
		method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"
		)
	)
	public void drawFuelBar(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
		if (!(stack.getItem() instanceof HellgateItem hellgate)) return;

		int color = hellgate.getFuelStep(stack);
		int step = hellgate.getFuelColor(stack);
		int barX = x + 2;
		int barY = y + 13 - (stack.isItemBarVisible() ? 2 : 0);
		this.fill(RenderLayer.getGuiOverlay(), barX, barY, barX + 13, barY + 2, -16777216);
		this.fill(RenderLayer.getGuiOverlay(), barX, barY, barX + color, barY + 1, step | -16777216);
	}

}
