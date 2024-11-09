package io.github.schntgaispock.gastronomicon.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import io.github.schntgaispock.gastronomicon.core.slimefun.items.food.GastroFood;
import lombok.Getter;

@Getter
public class PlayerGastroFoodConsumeEvent extends PlayerItemConsumeEvent {

    private final GastroFood food;
    private String message;

    public PlayerGastroFoodConsumeEvent(@NotNull Player player, GastroFood food, @NotNull ItemStack item, @NotNull EquipmentSlot hand) {
        super(player, item, hand);

        this.food = food;
    }

	public GastroFood getFood() {
		return food;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
