package com.sindercube.sorrowfall.registry;

import com.mojang.serialization.Codec;
import com.sindercube.sorrowfall.Sorrowfall;
import com.sindercube.sorrowfall.content.component.HellgateFuelComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {

	public static void init() {}

	public static final ComponentType<HellgateFuelComponent> HELLGATE_FUEL = register("hellgate_fuel", HellgateFuelComponent.CODEC);

	public static <T> ComponentType<T> register(String path, Codec<T> codec) {
		ComponentType<T> type = ComponentType.<T>builder()
			.codec(codec)
			.packetCodec(PacketCodecs.codec(codec))
			.build();
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Sorrowfall.of(path), type);
	}

}
