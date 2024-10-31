package com.sindercube.sorrowfall.content.item;

import com.sindercube.sorrowfall.content.component.HellgateFuelComponent;
import com.sindercube.sorrowfall.content.entity.WeakDragonFireballEntity;
import com.sindercube.sorrowfall.registry.ModEnchantmentEffectTypes;
import com.sindercube.sorrowfall.registry.ModSoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class HellgateItem extends Item {

	private final HellgateData data;

	public HellgateItem(HellgateData data, Settings settings) {
		super(settings);
		this.data = data;
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
		HellgateFuelComponent component = HellgateFuelComponent.get(stack);
		float current = component.getFuel(stack);
		float max = component.getMaxFuel(stack);
		return MathHelper.clamp(
			Math.round(13.0F - (max - current) * 13.0F / max),
			0, 13
		);
	}


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		HellgateFuelComponent component = HellgateFuelComponent.get(stack);

		if (component.getFuel(stack) == 0) {
			player.playSound(ModSoundEvents.ITEM_HELLGATE_FAIL);
			return TypedActionResult.fail(stack);
		}
		player.playSound(ModSoundEvents.ITEM_HELLGATE_USE);

		if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_DRAGON_FIREBALL)) {
			useDragonFireballAttack(world, player);
		} else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectTypes.HELLGATE_SOUL_FIRE_ATTACK)) {
			useSoulFireAttack(world, player);
		} else {
			useDefaultAttack(world, player);
		}

		return TypedActionResult.success(stack);
	}

	public void useDefaultAttack(World world, PlayerEntity player) {
	}

	public void useDragonFireballAttack(World world, PlayerEntity player) {
		Vec3d rotation = player.getRotationVector();
		DragonFireballEntity fireball = new WeakDragonFireballEntity(world, player, rotation);
		fireball.updatePosition(fireball.getX(), fireball.getY() + player.getHeight() / 2, fireball.getZ());
		world.spawnEntity(fireball);
		player.getItemCooldownManager().set(this, 200);
	}

	public void useSoulFireAttack(World world, PlayerEntity player) {
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltips, TooltipType type) {
		HellgateFuelComponent component = HellgateFuelComponent.get(stack);
		tooltips.add(component.getTooltip(stack));
	}


	public record HellgateData(
		int range,
		int maxCharges
	) {}

}
