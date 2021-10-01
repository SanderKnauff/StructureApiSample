package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.structure.Palette;
import org.bukkit.structure.Structure;

import java.io.File;
import java.io.IOException;

public class DescribeStructureCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return false;
        }

        NamespacedKey structureKey = NamespacedKey.fromString(args[0]);
        if (structureKey.getKey().contains("//")) {
            sender.sendMessage("Structure names can not contain \"//\"");
            return true;
        }
        Structure structure = Bukkit.getServer().getStructureManager().loadStructure(structureKey);

        sender.sendMessage(String.format("%s:%s contains the following blocks:", structureKey.getNamespace(), structureKey.getKey()));
        for (Palette palette : structure.getPalettes()) {
            for (BlockState blockState : palette.getBlocks()) {
                sender.sendMessage(String.format("%s x:%d y:%d z:%d", blockState.getBlockData().getMaterial(), blockState.getX(), blockState.getY(), blockState.getZ()));
            }
        }
        return true;
    }
}
