package com.sindercube.sorrowfall.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.sorrowfall.registry.ModComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public record HellgateFuelComponent(
	int fuel,
	int maxFuel
) {

	public static final HellgateFuelComponent DEFAULT = new HellgateFuelComponent(0, 12);

	public static final Codec<HellgateFuelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.optionalFieldOf("fuel", 0).forGetter(HellgateFuelComponent::fuel),
		Codec.INT.optionalFieldOf("max_fuel", 12).forGetter(HellgateFuelComponent::maxFuel)
	).apply(instance, HellgateFuelComponent::new));

	public static HellgateFuelComponent get(ItemStack stack) {
		return stack.getOrDefault(ModComponents.HELLGATE_FUEL, DEFAULT);
	}


	public Text getTooltip(ItemStack stack) {
//		HellgateFuelComponent component = HellgateFuelComponent.get(stack);
		return null;
	}

}
