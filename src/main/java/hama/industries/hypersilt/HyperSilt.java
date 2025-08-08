package hama.industries.hypersilt;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HyperSilt implements ModInitializer {
	public static final String MOD_ID = "hyper_silt";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier id(String id) {
		return Identifier.of(MOD_ID, id);
	}

	public static final RegistryKey<Item> HYPER_SILT_KEY = RegistryKey.of(RegistryKeys.ITEM, id("hyper_silt"));
	public static final Item HYPER_SILT = Registry.register(Registries.ITEM, HYPER_SILT_KEY, new Item(new Item.Settings().registryKey(HYPER_SILT_KEY).maxCount(1)));

	public static final ItemGroup SILT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(HYPER_SILT))
			.displayName(Text.translatable("itemGroup.hyper_silt"))
			.entries((context, entries) -> {
				entries.add(HYPER_SILT);
			})
			.build();

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, id("hyper_silt"), SILT_GROUP);
	}
}