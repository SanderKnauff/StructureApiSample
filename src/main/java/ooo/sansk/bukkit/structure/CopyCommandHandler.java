package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CopyCommandHandler implements TabExecutor {

    private final Map<UUID, Structure> clipboard;

    public CopyCommandHandler(Map<UUID, Structure> clipboard) {
        this.clipboard = clipboard;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a in-game player!");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 7) {
            return false;
        }

        int[] coords = new int[6];
        for (int i = 1; i <= 6; i++) {
            try {
                coords[i - 1] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                sender.sendMessage("\"" + args[i] + "\" is not a valid integer");
                return true;
            }
        }

        boolean includeEntities = true;
        if (args.length > 7) {
            if (!"true".equalsIgnoreCase(args[7]) && !"false".equalsIgnoreCase(args[7])) {
                sender.sendMessage("\"" + args[7] + "\" is not a valid boolean");
                return true;
            }
            includeEntities = Boolean.parseBoolean(args[7]);
        }

        boolean save = false;
        if (args.length > 8) {
            if (!"true".equalsIgnoreCase(args[8]) && !"false".equalsIgnoreCase(args[8])) {
                sender.sendMessage("\"" + args[8] + "\" is not a valid boolean");
                return true;
            }
            save = Boolean.parseBoolean(args[8]);
        }

        Location corner1 = new Location(player.getWorld(), coords[0], coords[1], coords[2]);
        Location corner2 = new Location(player.getWorld(), coords[3], coords[4], coords[5]);

        Structure structure = Bukkit.getServer().getStructureManager().createStructure();
        structure.fill(corner1, corner2, includeEntities);

        if (save) {
            if (args[0].startsWith("file::")) {
                sender.sendMessage("Copying: " + args[0]);
                try {
                    Bukkit.getServer().getStructureManager().saveStructure(new File(args[0].substring(6)), structure);
                } catch (IOException ioException) {
                    sender.sendMessage("Failed saving structure to " + args[0]);
                    ioException.printStackTrace();
                }
            } else {
                NamespacedKey structureKey = NamespacedKey.fromString(args[0]);
                if (structureKey.getKey().contains("//")) {
                    sender.sendMessage("Structure names can not contain \"//\"");
                    return true;
                }
                sender.sendMessage("Copying: " + args[0]);
                try {
                    Bukkit.getServer().getStructureManager().saveStructure(structureKey, structure);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        clipboard.put(player.getUniqueId(), structure);

        if (save) {
            sender.sendMessage("Copied and saved structure: " + args[0]);
        } else {
            sender.sendMessage("Copied structure: " + args[0]);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }
        Player player = (Player) sender;

        switch (args.length) {
            case 2:
            case 5:
                return Collections.singletonList(String.valueOf(player.getLocation().getBlockX()));
            case 3:
            case 6:
                return Collections.singletonList(String.valueOf(player.getLocation().getBlockY()));
            case 4:
            case 7:
                return Collections.singletonList(String.valueOf(player.getLocation().getBlockZ()));
            case 8:
            case 9:
                return Arrays.asList("true", "false");
            default:
                return Collections.emptyList();
        }
    }
}
