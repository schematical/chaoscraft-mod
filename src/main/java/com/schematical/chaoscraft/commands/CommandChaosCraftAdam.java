package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import io.mikael.urlbuilder.util.Decoder;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Created by user1a on 1/3/19.
 */
public class CommandChaosCraftAdam extends CommandBase {

  private static final Decoder UTF8_URL_ENCODER = new Decoder(StandardCharsets.UTF_8);

  /*
   * Gets the name of the command
   */
  public String getName() {
    return "chaoscraft-adam";
  }

  /**
   * Return the required permission level for this command.
   */
  public int getRequiredPermissionLevel() {
    return 2;
  }

  /**
   * Gets the usage string for the command.
   */
  public String getUsage(ICommandSender sender) {
    return "commands.chaoscraft-adam.usage";
  }

  /**
   * Callback for when the command is executed
   */
  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
      throws CommandException {
    if (!sender.getEntityWorld().isRemote) {
      return;
    }
    //Check to see if adam is alive? If so kill him

    if (ChaosCraft.adam != null) {
      ChaosCraft.adam.setDead();
      ChaosCraft.adam = null;
    }

    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String resourcePath = classLoader.getResource("adam-0.json").getFile();
      resourcePath = UTF8_URL_ENCODER.decodePath(resourcePath);
      File f = new File(resourcePath);
      JSONObject obj = null;
      if (!f.exists()) {
        ChaosCraft.chat("Error Spawning Adam: " + resourcePath + " does not exist");
        return;
      }
      if (f.isDirectory()) {
        ChaosCraft.chat("Error Spawning Adam: " + resourcePath + " is a directory");
        return;
      }
      JSONParser parser = new JSONParser();
      obj = (JSONObject) parser.parse(
          new FileReader(resourcePath)
      );
      //username = obj.get("username").toString();

      Organism organism = new Organism();
      organism.setGeneration(0d);
      organism.setName("adam");
      organism.setNamespace("adam");
      JSONObject nNet = (JSONObject) obj.get("nNet");
      organism.setNNetRaw(nNet.toJSONString());

      List<Organism> organismList = new ArrayList<Organism>();
      organismList.add(organism);
      List<EntityOrganism> entitys = ChaosCraft.spawnOrgs(organismList);
      if (entitys.size() != 1) {
        throw new ChaosNetException(
            "Did not get the right amount of adams back: " + entitys.size());
      }
      entitys.forEach((EntityOrganism org) -> {
        org.setDebug(true);
        org.setSkin("chaoscraft:batman.png");
        org.adjustMaxLife(1000);
        org.setCustomNameTag("adam");
      });
      ChaosCraft.chat("Adam Successfully Spawned!");
      ChaosCraft.adam = entitys.get(0);
    } catch (Exception e) {
      ChaosCraft.logger.error(e.getMessage());
      e.printStackTrace();
      ChaosCraft.chat("Error Spawning Adam: " + e.getMessage());

    }

    //Spawn an Adam-> Change skin

  }


}
