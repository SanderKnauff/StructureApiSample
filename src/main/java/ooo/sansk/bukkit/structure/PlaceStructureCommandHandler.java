package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
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
import java.util.Random;
import java.util.stream.Collectors;

public class PlaceStructureCommandHandler implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a in-game player!");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 1) {
            return false;
        }
        String structureName = args[0];

        if (structureName.contains("//")) {
            sender.sendMessage("Structure names can not contain \"//\"");
            return true;
        }

        boolean includeEntities = true;
        if (args.length > 1) {
            if(!"true".equalsIgnoreCase(args[1]) && !"false".equalsIgnoreCase(args[1])) {
                sender.sendMessage("\"" + args[1] + "\" is not a valid boolean");
                return true;
            }
            includeEntities = Boolean.parseBoolean(args[1]);
        }

        StructureRotation rotation = StructureRotation.NONE;
        if (args.length > 2) {
            try {
                rotation = StructureRotation.valueOf(args[2]);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("\"" + args[2] + "\" is not a valid rotation");
                return true;
            }
        }

        Mirror mirror = Mirror.NONE;
        if (args.length > 3) {
            try {
                mirror = Mirror.valueOf(args[3]);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("\"" + args[3] + "\" is not a valid mirror option");
                return true;
            }
        }

        float integrity = 1F;
        if (args.length > 4) {
            try {
                integrity = Float.parseFloat(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage("\"" + args[4] + "\" is not a valid float");
                return true;
            }
        }

        if(integrity < 0F || integrity > 1F) {
            sender.sendMessage("Integrity must be between 0 and 1 (inclusive)");
            return true;
        }

        int palette = -1;
        if (args.length > 5) {
            try {
                palette = Integer.parseInt(args[5]);
            } catch (NumberFormatException e) {
                sender.sendMessage("\"" + args[5] + "\" is not a valid integer");
                return true;
            }
        }

        final Structure structure = Bukkit.getServer().getStructureManager().load(structureName);
        if (structure == null) {
            sender.sendMessage("Structure \"" + structureName + "\" not found.");
            return true;
        }

        if(palette >= structure.getPalettes().size()) {
            sender.sendMessage("Invalid structure palette. Highest possible index is " + structure.getPalettes().size());
            return true;
        }

        structure.place(player.getLocation(), includeEntities, rotation, mirror, palette, integrity, new Random());
        sender.sendMessage("Placed \"" + structure.getName() + "\"");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 2:
                return Arrays.asList("true", "false");
            case 3:
                return Arrays.stream(StructureRotation.values()).map(Enum::name).collect(Collectors.toList());
            case 4:
                return Arrays.stream(Mirror.values()).map(Enum::name).collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }
}
