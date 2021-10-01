package ooo.sansk.bukkit.structure;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.structure.Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Plugin(name = "StructureApiSample", version = "1.0")
@Command(name = "placestructure", usage = "/placestructure <StructureName>")
@Command(name = "loadstructure", usage = "/loadstructure <StructureName>")
@Command(name = "copy", usage = "/copy <StructureName> <x1> <y1> <z1> <z2> <y2> <z2> <includeEntities> <save>")
@Command(name = "paste", usage = "/paste")
@Command(name = "deletestructure", usage = "/deletestructure <StructureName> <keepLoaded>")
@Command(name = "liststructures", usage = "/liststructures")
@Command(name = "cloneStructure", usage = "/cloneStructure <StructureName> <target>")
@Command(name = "describestructure", usage = "/describestructure <StructureName>")
@ApiVersion(ApiVersion.Target.v1_13)
public class StructureApiSamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Map<UUID, Structure> clipboard = new HashMap<>();
        getCommand("placestructure").setExecutor(new PlaceStructureCommandHandler());
        getCommand("copy").setExecutor(new CopyCommandHandler(clipboard));
        getCommand("paste").setExecutor(new PasteCommandHandler(clipboard));
        getCommand("deletestructure").setExecutor(new DeleteStructureCommandHandler());
        getCommand("liststructures").setExecutor(new ListStructuresCommandHandler());
        getCommand("loadstructure").setExecutor(new LoadStructureCommandHandler(this));
        getCommand("cloneStructure").setExecutor(new CloneCommandHandler());
        getCommand("describestructure").setExecutor(new DescribeStructureCommandHandler());

//        Bukkit.getServer().getWorlds().get(0).getPopulators().add(new ShipSpammerPopulator());
    }
}
