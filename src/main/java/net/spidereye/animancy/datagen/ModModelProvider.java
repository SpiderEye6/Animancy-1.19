package net.spidereye.animancy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.spidereye.animancy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SOUL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.REVENANT_SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRAGON_SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRACONIC_RISEN_SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_STEEL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ANIMANTIC_WAR_SCYTHE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SOUL_CHECKER_DEBUG, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ANIMANCY_CHECKER_DEBUG, Models.HANDHELD);
    }
}
