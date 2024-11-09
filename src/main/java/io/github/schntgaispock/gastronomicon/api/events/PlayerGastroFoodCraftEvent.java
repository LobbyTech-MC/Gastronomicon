package io.github.schntgaispock.gastronomicon.api.events;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import io.github.schntgaispock.gastronomicon.api.recipes.GastroRecipe;
import lombok.Getter;
import lombok.Setter;

@Getter 
public class PlayerGastroFoodCraftEvent extends PlayerEvent implements Cancellable {

    private static final @Getter HandlerList handlerList = new HandlerList();

    private @Setter boolean cancelled;
    private final GastroRecipe recipe;
    private @Setter String message;

    @ParametersAreNonnullByDefault
    public PlayerGastroFoodCraftEvent(Player player, GastroRecipe recipe) {
        super(player);

        this.recipe = recipe;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		// TODO Auto-generated method stub
		this.cancelled = true;
	}

	public GastroRecipe getRecipe() {
		return recipe;
	}

	public @NotNull String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
