package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class DeleteStructureCommandHandler implements CommandExecutor {

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
        try {
            Bukkit.getServer().getStructureManager().delete(structureKey);
            sender.sendMessage("Deleted \"" + args[0] + "\"");
        } catch (IOException ioException) {
            sender.sendMessage("Failed deleting \"" + args[0] + "\"");
        }
        sender.sendMessage("Unloaded \"" + args[0] + "\"");
        return true;
    }
}
