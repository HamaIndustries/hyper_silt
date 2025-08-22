package hama.industries.hypersilt;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Models;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class HyperSiltDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(HyperSiltModels::new);
        pack.addProvider(HSItemTags::new);
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
            itemModelGenerator.output.accept(
                    HyperSilt.OMNI_CLAY,
                    ItemModels.condition(
                            new BLJProperty(),
                            ItemModels.basic(itemModelGenerator.registerSubModel(HyperSilt.OMNI_CLAY, "_active", Models.GENERATED)),
                            ItemModels.basic(itemModelGenerator.registerSubModel(HyperSilt.OMNI_CLAY, "", Models.GENERATED))
                    )
            );
            itemModelGenerator.output.accept(
                    HyperSilt.HYPER_SILT,
                    ItemModels.condition(
                            new BLJProperty(),
                            ItemModels.basic(itemModelGenerator.registerSubModel(HyperSilt.HYPER_SILT, "_active", Models.GENERATED)),
                            ItemModels.basic(itemModelGenerator.registerSubModel(HyperSilt.HYPER_SILT, "", Models.GENERATED))
                    )
            );
        }
    }

    private static class HSItemTags extends FabricTagProvider.ItemTagProvider {

        public HSItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getTagBuilder(HyperSilt.SPEEDRUNNING_SOILS)
                    .add(HyperSilt.HYPER_SILT_KEY.getValue())
                    .add(HyperSilt.OMNI_CLAY_KEY.getValue());
        }
    }
}
