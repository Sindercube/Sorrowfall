package com.sindercube.sorrowfall.content.item;

import com.sindercube.sorrowfall.content.component.HellgateFuelComponent;
import com.sindercube.sorrowfall.content.entity.WeakDragonFireballEntity;
import com.sindercube.sorrowfall.registry.ModEnchantmentEffectTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.awt.*;
import java.util.List;

public class HellgateItem extends Item {

	private final HellgateData data;

	public HellgateItem(HellgateData data, Settings settings) {
		super(settings);
		this.data = data;
	}

	public int getFuel(ItemStack stack) {
		return HellgateFuelComponent.get(stack).fuel();
	}

	public int getMaxFuel(ItemStack stack) {
		MutableFloat maxFuelVal = new MutableFloat(HellgateFuelComponent.get(stack).maxFuel());

		ItemEnchantmentsComponent enchants = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantmentWithLevel : enchants.getEnchantmentEntries()) {
			int level = enchantmentWithLevel.getIntValue();
			Enchantment enchantment = enchantmentWithLevel.getKey().value();
			enchantment.modifyValue(ModEnchantmentEffectTypes.HELLGATE_MAX_FUEL, null, level, maxFuelVal);
		}

		return maxFuelVal.intValue();
	}


	public static final int DEFAULT_COLOR = Color.decode("#FFFF00").getRGB();
	public static final int DRAGON_COLOR = Color.decode("#FF00FF").getRGB();
	public static final int SOUL_COLOR = Color.decode("#00FFFF").getRGB();

	public int getFuelColor(ItemStack stack) {
		if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_DRAGON_FIREBALL)) {
			return DRAGON_COLOR;
		} else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_SOUL_FIRE_ATTACK)) {
			return SOUL_COLOR;
		} else {
			return DEFAULT_COLOR;
		}
	}

	public int getFuelStep(ItemStack stack) {
		float current = this.getFuel(stack);
		float max = this.getMaxFuel(stack);
		return MathHelper.clamp(
			Math.round(13.0F - (max - current) * 13.0F / max),
			0, 13
		);
	}


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_DRAGON_FIREBALL)) {
			return useDragonFireballAttack(world, player, stack);
		} else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_SOUL_FIRE_ATTACK)) {
			return useSoulFireAttack(world, player, stack);
		} else {
			return useDefaultAttack(world, player, stack);
		}
	}

	public TypedActionResult<ItemStack> useDefaultAttack(World world, PlayerEntity player, ItemStack stack) {
		return TypedActionResult.success(stack);
	}

	public TypedActionResult<ItemStack> useDragonFireballAttack(World world, PlayerEntity player, ItemStack stack) {
		Vec3d rotation = player.getRotationVector();
		DragonFireballEntity fireball = new WeakDragonFireballEntity(world, player, rotation);
		fireball.updatePosition(fireball.getX(), fireball.getY() + player.getHeight() / 2, fireball.getZ());
		world.spawnEntity(fireball);
		player.getItemCooldownManager().set(this, 200);
		return TypedActionResult.success(stack);
	}

	public TypedActionResult<ItemStack> useSoulFireAttack(World world, PlayerEntity player, ItemStack stack) {
		return TypedActionResult.success(stack);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltips, TooltipType type) {
//		HellgateFuelComponent component = HellgateFuelComponent.get(stack);
//		tooltips.add(component.getTooltip(stack));
		super.appendTooltip(stack, context, tooltips, type);
		Text fuelText = Text.translatable("component.sorrowfall.hellgate_fuel.tooltip", getFuel(stack), getMaxFuel(stack));
		tooltips.add(fuelText);
	}

	public record HellgateData(
		int range,
		int maxCharges
	) {}

}
