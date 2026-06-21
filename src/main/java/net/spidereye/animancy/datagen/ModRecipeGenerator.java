package net.spidereye.animancy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(ModItems.SOUL_STEEL_INGOT)
                .pattern("SN")
                .pattern("RS")
                .input('N', Items.NETHERITE_INGOT)
                .input('S', ModItems.SOUL_SHARD)
                .input('R', ModItems.REVENANT_SOUL)
                .criterion(RecipeProvider.hasItem(Items.NETHERITE_INGOT),
                        RecipeProvider.conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(RecipeProvider.hasItem(ModItems.SOUL_SHARD),
                        RecipeProvider.conditionsFromItem(ModItems.SOUL_SHARD))
                .criterion(RecipeProvider.hasItem(ModItems.REVENANT_SOUL),
                        RecipeProvider.conditionsFromItem(ModItems.REVENANT_SOUL))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.SOUL_STEEL_INGOT)));

        ShapedRecipeJsonBuilder.create(ModItems.SOUL_STEEL_SWORD)
                .pattern("I")
                .pattern("I")
                .pattern("S")
                .input('I', ModItems.SOUL_STEEL_INGOT)
                .input('S', Items.STICK)
                .criterion(RecipeProvider.hasItem(ModItems.SOUL_STEEL_INGOT),
                        RecipeProvider.conditionsFromItem(ModItems.SOUL_STEEL_INGOT))
                .criterion(RecipeProvider.hasItem(Items.STICK),
                        RecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.SOUL_STEEL_SWORD)));

        ShapedRecipeJsonBuilder.create(ModItems.ANIMANTIC_WAR_SCYTHE)
                .pattern("IIS")
                .pattern(" D ")
                .pattern("S  ")
                .input('I', ModItems.SOUL_STEEL_INGOT)
                .input('D', ModItems.DRACONIC_RISEN_SOUL)
                .input('S', Items.STICK)
                .criterion(RecipeProvider.hasItem(ModItems.SOUL_STEEL_INGOT),
                        RecipeProvider.conditionsFromItem(ModItems.SOUL_STEEL_INGOT))
                .criterion(RecipeProvider.hasItem(ModItems.DRACONIC_RISEN_SOUL),
                        RecipeProvider.conditionsFromItem(ModItems.DRACONIC_RISEN_SOUL))
                .criterion(RecipeProvider.hasItem(Items.STICK),
                        RecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.ANIMANTIC_WAR_SCYTHE)));
    }
}
