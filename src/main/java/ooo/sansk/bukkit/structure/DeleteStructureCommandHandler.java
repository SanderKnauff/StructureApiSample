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
        if (args.length == 1 ) {
            return false;
        }

        boolean unload = true;
        if (args.length > 1) {
            if (!"true".equalsIgnoreCase(args[1]) && !"false".equalsIgnoreCase(args[1])) {
                sender.sendMessage("\"" + args[1] + "\" is not a valid boolean");
                return true;
            }
            unload = Boolean.parseBoolean(args[1]);
        }

        NamespacedKey structureKey = NamespacedKey.fromString(args[0]);
        if (structureKey.getKey().contains("//")) {
            sender.sendMessage("Structure names can not contain \"//\"");
            return true;
        }
        try {
            Bukkit.getServer().getStructureManager().deleteStructure(structureKey, unload);
            sender.sendMessage("Deleted \"" + args[0] + "\"");
        } catch (IOException ioException) {
            sender.sendMessage("Failed deleting \"" + args[0] + "\"");
        }
        sender.sendMessage("Unloaded \"" + args[0] + "\"");
        return true;
    }
}
