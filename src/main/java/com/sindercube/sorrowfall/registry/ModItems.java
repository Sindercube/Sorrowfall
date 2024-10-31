package com.sindercube.sorrowfall.registry;

import com.sindercube.sorrowfall.Sorrowfall;
import com.sindercube.sorrowfall.content.component.HellgateFuelComponent;
import com.sindercube.sorrowfall.content.item.HellgateItem;
import com.sindercube.sorrowfall.content.item.IceRodItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

	public static void init() {}

	public static final Item HELLGATE = register("hellgate", new HellgateItem(
		new HellgateItem.HellgateData(12, 9	),
		new Item.Settings()
			.maxCount(1)
			.maxDamage(1024)
			.component(ModComponents.HELLGATE_FUEL, new HellgateFuelComponent(12, 12))
	));
	public static final Item ICE_ROD = register("ice_rod", new IceRodItem(
		new Item.Settings()
			.maxCount(1)
	));

	public static Item register(String path, Item item) {
		return Registry.register(Registries.ITEM, Sorrowfall.of(path), item);
	}

}
