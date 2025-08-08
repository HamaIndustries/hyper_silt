package hama.industries.hypersilt;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Items;

public class HyperSiltDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

	}

	public static final class HyperSiltModels extends FabricModelProvider {

		public HyperSiltModels(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.registerWithTextureSource(HyperSilt.HYPER_SILT, Items.CLAY_BALL, Models.GENERATED);
		}
	}
}
