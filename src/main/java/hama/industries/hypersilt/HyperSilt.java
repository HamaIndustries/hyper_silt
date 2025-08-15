package hama.industries.hypersilt;

import it.unimi.dsi.fastutil.objects.Object2ReferenceAVLTreeMap;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

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
	public static final Item HYPER_SILT = Registry.register(Registries.ITEM, HYPER_SILT_KEY, new Item(new Item.Settings()
			.registryKey(HYPER_SILT_KEY)
			.maxCount(1)
			.jukeboxPlayable(RegistryKey.of(RegistryKeys.JUKEBOX_SONG, id("ballsfish")))
	));

	public static final ItemGroup SILT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(HYPER_SILT))
			.displayName(Text.translatable("itemGroup.hyper_silt"))
			.entries((context, entries) -> {
				entries.add(HYPER_SILT);
			})
			.build();

	public static final AttachmentType<Boolean> HYPER = AttachmentRegistry.create(id("hyper"), builder -> builder.syncWith(PacketCodecs.BOOLEAN, AttachmentSyncPredicate.all()));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, id("hyper_silt"), SILT_GROUP);
//		ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> entity.setAttached(HYPER, true));
		ServerTickEvents.START_SERVER_TICK.register(HyperSilt::hyperize);
	}

	private static Map<UUID, Vec3d> positions = new Object2ReferenceAVLTreeMap<>();
	private static void hyperize(MinecraftServer server) {
		for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
			Vec3d old = positions.computeIfAbsent(player.getUuid(), u -> player.getPos());
			Vec3d delta = old.subtract(player.getPos());
			double speed = old.subtract(player.getPos()).length();
			boolean hyper = delta.dotProduct(player.getRotationVector()) > 0 && speed > 0.7;
			positions.put(player.getUuid(), player.getPos());
			if (player.getStackInHand(Hand.MAIN_HAND).isOf(HYPER_SILT) && hyper){
				player.setAttached(HYPER, true);
			} else {
				player.removeAttached(HYPER);
			}
		}
	}
}