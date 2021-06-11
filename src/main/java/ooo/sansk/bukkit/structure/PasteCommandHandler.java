package ooo.sansk.bukkit.structure;

import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class PasteCommandHandler implements TabExecutor {

    private final Map<UUID, Structure> clipboard;

    public PasteCommandHandler(Map<UUID, Structure> clipboard) {
        this.clipboard = clipboard;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a in-game player!");
            return true;
        }
        Player player = (Player) sender;

        boolean includeEntities = true;
        if (args.length > 0) {
            if (!"true".equalsIgnoreCase(args[0]) && !"false".equalsIgnoreCase(args[1])) {
                sender.sendMessage("\"" + args[0] + "\" is not a valid boolean");
                return true;
            }
            includeEntities = Boolean.parseBoolean(args[0]);
        }

        StructureRotation rotation = StructureRotation.NONE;
        if (args.length > 1) {
            try {
                rotation = StructureRotation.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("\"" + args[1] + "\" is not a valid rotation");
                return true;
            }
        }

        Mirror mirror = Mirror.NONE;
        if (args.length > 2) {
            try {
                mirror = Mirror.valueOf(args[2]);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("\"" + args[2] + "\" is not a valid mirror option");
                return true;
            }
        }

        float integrity = 1F;
        if (args.length > 3) {
            try {
                integrity = Float.parseFloat(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("\"" + args[3] + "\" is not a valid float");
                return true;
            }
        }

        if (integrity < 0F || integrity > 1F) {
            sender.sendMessage("Integrity must be between 0 and 1 (inclusive)");
            return true;
        }

        int palette = -1;
        if (args.length > 4) {
            try {
                palette = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage("\"" + args[4] + "\" is not a valid integer");
                return true;
            }
        }

        final Structure structure = clipboard.get(player.getUniqueId());
        if (structure == null) {
            sender.sendMessage("Clipboard was empty.");
            return true;
        }

        if (palette >= structure.getPalettes().size()) {
            sender.sendMessage("Invalid structure palette. Highest possible index is " + structure.getPalettes().size());
            return true;
        }

        structure.place(player.getLocation(), includeEntities, rotation, mirror, palette, integrity, new Random());
        sender.sendMessage("Placed structure from clipboard");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return Arrays.asList("true", "false");
            case 2:
                return Arrays.stream(StructureRotation.values()).map(Enum::name).collect(Collectors.toList());
            case 3:
                return Arrays.stream(Mirror.values()).map(Enum::name).collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }

}
