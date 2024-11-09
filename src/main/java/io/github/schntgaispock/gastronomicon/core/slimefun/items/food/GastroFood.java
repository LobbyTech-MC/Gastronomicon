package io.github.schntgaispock.gastronomicon.core.slimefun.items.food;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.schntgaispock.gastronomicon.api.events.PlayerGastroFoodConsumeEvent;
import io.github.schntgaispock.gastronomicon.api.food.FoodEffect;
import io.github.schntgaispock.gastronomicon.api.items.FoodItemStack;
import io.github.schntgaispock.gastronomicon.api.recipes.GastroRecipe;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import lombok.Getter;
import net.kyori.adventure.text.Component;

public class GastroFood extends SimpleGastroFood {

    private static final Set<String> gastroFoodIds = new HashSet<>();

    private final @Getter FoodItemStack item;
    private final @Getter boolean perfect;
    private final ItemStack recipeDisplayOutput;

	private GastroRecipe recipe;

    public GastroFood(Research research, ItemGroup group, FoodItemStack item, GastroRecipe recipe,
        ItemStack topRightDisplayItem, ItemStack recipeDisplayOutput, boolean perfect) {
        super(research, group, item, recipe, topRightDisplayItem, recipeDisplayOutput, !perfect);

        this.item = item;
        this.perfect = perfect;
        this.recipeDisplayOutput = recipeDisplayOutput;
        this.recipe = recipe;
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler) this::onRightClick);
        super.preRegister();
    }

    public void onRightClick(@Nonnull PlayerRightClickEvent e) {
        if (e.getPlayer().getFoodLevel() >= 20) {
            e.cancel();
            return; // Can't eat when full
        }

        final SlimefunItem sfItem = SlimefunItem.getByItem(e.getItem());
        if (sfItem == null)
            return;

        if (sfItem instanceof final GastroFood food) {
            e.cancel();

            final PlayerGastroFoodConsumeEvent consumeEvent = new PlayerGastroFoodConsumeEvent(e.getPlayer(), food,
                e.getItem(), e.getHand());
            consumeEvent.callEvent();
            if (consumeEvent.isCancelled()) {
                if (consumeEvent.getMessage() != null)
                    e.getPlayer().sendMessage(Component.text(consumeEvent.getMessage()));
                return;
            }

            final Player p = e.getPlayer();
            for (org.bukkit.inventory.meta.components.FoodComponent.FoodEffect effect : food.getItem().getItemMeta().getFood().getEffects()) {
                ((FoodEffect) effect).apply(p, food.isPerfect());
            }
            p.setFoodLevel(Math.min(p.getFoodLevel() + (int) food.getItem().getItemMeta().getFood().getNutrition(), 20));
            p.setSaturation((float) Math.min(p.getSaturation() + food.getItem().getItemMeta().getFood().getSaturation(),
                p.getFoodLevel()));
            if (getGastroRecipe().getInputs().getContainer().getComponent() instanceof final ItemStack stack) {
                p.getInventory().addItem(stack); // It should always be an itemstack anyways
            }
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1, 1);
        }

        e.getItem().subtract();
    }

    public GastroRecipe getGastroRecipe() {
		// TODO Auto-generated method stub
		return recipe;
	}

	/**
     * Will create a hidden perfect version of itself if not already perfect.
     */
    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        if (!isPerfect()) {
            getGastroFoodIds().add(getId());
            new GastroFood(getResearch(), getItemGroup(), item.asPerfect(), getGastroRecipe(), topRightDisplayItem,
                recipeDisplayOutput, true).hide()
                    .register(addon);
        }
    }

	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}

	public static Set<String> getGastroFoodIds() {
		// TODO Auto-generated method stub
		return gastroFoodIds;
	}

}
