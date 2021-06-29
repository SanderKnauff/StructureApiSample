package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.structure.Structure;

import java.io.File;
import java.io.IOException;

public class CloneCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 2) {
            return false;
        }

        NamespacedKey structureKey = NamespacedKey.fromString(args[0]);
        if (structureKey.getKey().contains("//")) {
            sender.sendMessage("Structure names can not contain \"//\"");
            return true;
        }
        Structure structure = Bukkit.getServer().getStructureManager().loadStructure(structureKey);

        final Structure copy = Bukkit.getServer().getStructureManager().copy(structure);
        try {
            sender.sendMessage("Cloned " + args[0] + " to " + args[1]);
            Bukkit.getServer().getStructureManager().saveStructure(new File(args[1]), copy);
        } catch (IOException ioException) {
            sender.sendMessage("Yeah.. That cloning stuff... Didn't work.");
            ioException.printStackTrace();
        }
        return true;
    }
}
