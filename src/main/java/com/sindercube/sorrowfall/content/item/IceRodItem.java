package com.sindercube.sorrowfall.content.item;

import com.sindercube.sorrowfall.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class IceRodItem extends Item {

	public IceRodItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		Vec3d eyePos = player.getEyePos();
		Vec3d rotation = player.getRotationVector(player.getPitch(), player.getYaw());
		Vec3d offset = eyePos.add(rotation.multiply(player.getBlockInteractionRange()));
		BlockHitResult blockHitResult = world.raycast(
			new RaycastContext(eyePos, offset, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.SOURCE_ONLY, player)
		);
		if (blockHitResult.getType() == HitResult.Type.BLOCK) return TypedActionResult.fail(stack);

		BlockPos pos = blockHitResult.getBlockPos();
		return new TypedActionResult<>(trySetBlock(world, pos), stack);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		World world = player.getWorld();
		return trySetBlock(world, entity.getBlockPos());
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();

		ActionResult tryReplace = trySetBlock(world, pos);
		if (tryReplace.isAccepted()) return tryReplace;

		BlockPos newPos = pos.offset(context.getSide());
		return trySetBlock(world, newPos);
	}

	public ActionResult trySetBlock(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (!state.isReplaceable()) return ActionResult.FAIL;

		world.setBlockState(pos, ModBlocks.THIN_ICE.getDefaultState());
		return ActionResult.SUCCESS;
	}

}
