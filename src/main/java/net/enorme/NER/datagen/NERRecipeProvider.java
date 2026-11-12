package net.enorme.NER.datagen;

import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.item.NERItems;
import net.enorme.NER.utils.CommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class NERRecipeProvider extends RecipeProvider {
    private static final String HAS_PLANKS = "has_planks";
    private static final String HAS_RAW_TAIL = "has_raw_indigo_salamander_tail";

    public NERRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ItemLike planks = NERBlocks.INDIGO_PLANKS.get();
        Ingredient plankIngredient = Ingredient.of(planks);

        planksFromLog(output, planks, NERTags.Items.INDIGO_STEMS, 4);
        woodFromLogs(output, NERBlocks.INDIGO_HYPHAE.get(), NERBlocks.INDIGO_STEM.get());
        woodFromLogs(output, NERBlocks.STRIPPED_INDIGO_HYPHAE.get(), NERBlocks.STRIPPED_INDIGO_STEM.get());

        stairBuilder(NERBlocks.INDIGO_STAIRS.get(), plankIngredient)
                .group("wooden_stairs").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, NERBlocks.INDIGO_SlAB.get(), plankIngredient)
                .group("wooden_slab").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        buttonBuilder(NERBlocks.INDIGO_BUTTON.get(), plankIngredient)
                .group("wooden_button").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        pressurePlateBuilder(RecipeCategory.REDSTONE, NERBlocks.INDIGO_PRESSURE_PLATE.get(), plankIngredient)
                .group("wooden_pressure_plate").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        fenceBuilder(NERBlocks.INDIGO_FENCE.get(), plankIngredient)
                .group("wooden_fence").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        fenceGateBuilder(NERBlocks.INDIGO_FENCE_GATE.get(), plankIngredient)
                .group("wooden_fence_gate").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        doorBuilder(NERBlocks.INDIGO_DOOR.get(), plankIngredient)
                .group("wooden_door").unlockedBy(HAS_PLANKS, has(planks)).save(output);
        trapdoorBuilder(NERBlocks.INDIGO_TRAPDOOR.get(), plankIngredient)
                .group("wooden_trapdoor").unlockedBy(HAS_PLANKS, has(planks)).save(output);

        ItemLike rawTail = NERItems.RAW_INDIGO_SALAMANDER_TAIL.get();
        ItemLike cookedTail = NERItems.COOKED_INDIGO_SALAMANDER_TAIL.get();

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawTail), RecipeCategory.FOOD, cookedTail, 0.45F, 300)
                .unlockedBy(HAS_RAW_TAIL, has(rawTail))
                .save(output);
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(rawTail), RecipeCategory.FOOD, cookedTail, 0.45F, 100)
                .unlockedBy(HAS_RAW_TAIL, has(rawTail))
                .save(output, CommonUtils.resourcePath("cooked_indigo_salamander_tail_from_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(rawTail), RecipeCategory.FOOD, cookedTail, 0.45F, 600)
                .unlockedBy(HAS_RAW_TAIL, has(rawTail))
                .save(output, CommonUtils.resourcePath("cooked_indigo_salamander_tail_from_campfire_cooking"));
    }
}
