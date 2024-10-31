package com.sindercube.sorrowfall.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.sorrowfall.registry.ModComponents;
import com.sindercube.sorrowfall.registry.ModEnchantmentEffectTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
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


	public int getFuel(ItemStack stack) {
		return fuel;
	}

	public int getMaxFuel(ItemStack stack) {
		MutableFloat maxFuelVal = new MutableFloat(maxFuel);

		ItemEnchantmentsComponent enchants = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantmentWithLevel : enchants.getEnchantmentEntries()) {
			int level = enchantmentWithLevel.getIntValue();
			Enchantment enchantment = enchantmentWithLevel.getKey().value();
			enchantment.modifyValue(ModEnchantmentEffectTypes.HELLGATE_MAX_FUEL, null, level, maxFuelVal);
		}

		return maxFuelVal.intValue();
	}

	public Text getTooltip(ItemStack stack) {
		return Text.translatable("component.sorrowfall.hellgate_fuel.tooltip", getFuel(stack), getMaxFuel(stack));
	}

}
