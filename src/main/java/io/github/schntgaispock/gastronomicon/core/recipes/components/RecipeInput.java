package io.github.schntgaispock.gastronomicon.core.recipes.components;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.schntgaispock.gastronomicon.core.recipes.GastroRecipe;
import io.github.schntgaispock.gastronomicon.util.recipe.RecipeUtil;
import io.github.schntgaispock.gastronomicon.core.recipes.GastroRecipe.RecipeShape;
import lombok.Getter;
import lombok.ToString;

/**
 * Stores information about the input of a {@link GastroRecipe}. Does not
 * include tools.
 */
@Getter
@ToString
public class RecipeInput {

    private final RecipeShape shapedness;
    private final RecipeComponent<?> container;
    private final RecipeComponent<?>[] ingredients;

    public RecipeInput(RecipeShape shapedness, RecipeComponent<?> container,
            RecipeComponent<?>... ingredients) {

        this.shapedness = shapedness;

        if (shapedness == RecipeShape.SHAPELESS) {
            Arrays.sort(ingredients, RecipeUtil::compareComponents);
        }
        this.ingredients = new RecipeComponent<?>[9];
        for (int i = 0; i < Math.min(ingredients.length, 9); i++) {
            this.ingredients[i] = ingredients[i];
        }

        this.container = container;
    }

    public ItemStack[] getDisplayIngredients() {
        return Arrays.stream(ingredients)
                .map(ingredient -> ingredient == null ? new ItemStack(Material.AIR) : ingredient.getComponent())
                .toArray(ItemStack[]::new);
    }

    @Override
    @SuppressWarnings("null")
    public int hashCode() {
        int hash = 1;

        hash = hash * 31 + shapedness.hashCode();
        if (container != null) {
            hash = hash * 31 + container.hashCode();
        }

        for (RecipeComponent<?> ingredient : ingredients) {
            hash = hash * 31 + ingredient.hashCode();
        }

        return hash;
    }

}