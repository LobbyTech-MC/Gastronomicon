package io.github.schntgaispock.gastronomicon.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import io.github.schntgaispock.gastronomicon.Gastronomicon;
import io.github.schntgaispock.gastronomicon.api.trees.TreeStructure;
import net.guizhanss.guizhanlib.slimefun.utils.NewBlockStorageUtil;

public class TreeGrowthListener implements Listener {

    public static void setup() {
        Bukkit.getPluginManager().registerEvents(new TreeGrowthListener(), Gastronomicon.getInstance());
    }

    @EventHandler
    public void onTreeGrow(StructureGrowEvent e) {
        Location l = e.getLocation();
        if (!NewBlockStorageUtil.hasBlock(l)) {
            return;
        }
        var blockData = NewBlockStorageUtil.getBlock(l);
        final String sapling = blockData.getSfId();
        final TreeStructure tree = TreeStructure.getLoadedTrees().get(sapling);
        if (tree == null) {
            return;
        }

        e.setCancelled(true);
        NewBlockStorageUtil.removeBlock(l);

        Gastronomicon.getInstance().getScheduler().run(() -> {
            try {
                tree.build(l, sapling);
            } catch (NullPointerException | IllegalArgumentException err) {
                err.printStackTrace();
            }
        });

    }
}
