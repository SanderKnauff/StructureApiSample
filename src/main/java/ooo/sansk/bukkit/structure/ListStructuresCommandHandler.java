package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListStructuresCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        sender.sendMessage("Structures in cache:");
        Bukkit.getServer().getStructureManager().getStructures().keySet().stream()
            .map(key -> String.format("%s:%s @ %s", key.getNamespace(), key.getKey(), Bukkit.getServer().getStructureManager().getStructureFile(key)))
            .forEach(sender::sendMessage);
        return true;
    }
}
