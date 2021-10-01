package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;

import java.util.Random;

public class ShipSpammerPopulator extends BlockPopulator {

    @Override
    public void populate(WorldInfo worldInfo, Random random, int x, int z, LimitedRegion limitedRegion) {
        final Structure structure = Bukkit.getServer().getStructureManager().loadStructure(NamespacedKey.minecraft("shipwreck/with_mast"));
        if (structure == null) {
            System.out.println("Structure did not exist while generating");
            return;
        }
        structure.place(
            limitedRegion,
            new BlockVector(x * 16, 64, z * 16),
            true,
            StructureRotation.values()[random.nextInt(StructureRotation.values().length)],
            Mirror.values()[random.nextInt(Mirror.values().length)],
            random.nextInt(structure.getPaletteCount()),
            1,
            random
        );
    }
}
