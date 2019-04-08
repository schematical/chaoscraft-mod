package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by user1a on 12/7/18.
 */
public class AISpawnOrganisim extends EntityAIBase {

  protected EntityRick rick;

  public AISpawnOrganisim(EntityRick _rick) {
    super();
    this.rick = _rick;
  }

  public boolean shouldExecute() {
    int liveOrgCount = 0;
    for (EntityOrganism organism : ChaosCraft.organisims) {
      if (!organism.isDead) {
        liveOrgCount += 1;
      }
    }
    if (
        ChaosCraft.ticksSinceLastSpawn < (20 * 20) ||
            liveOrgCount >= ChaosCraft.config.maxBotCount
    ) {
      return false;
    }
    return true;
  }

  public boolean shouldContinueExecuting() {

    return false;//super.shouldContinueExecuting();
  }

  public void startExecuting() {

    List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
    Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

    while (iterator.hasNext()) {
      EntityOrganism organism = iterator.next();
      if (organism.isDead) {
        if (
            organism.getCCNamespace() != null &&
                organism.getSpawnHash() == ChaosCraft.spawnHash &&
                !organism.getDebug()//Dont report Adam-0
        ) {
          deadOrgs.add(organism);
        }
        //ChaosCraft.logger.info("Removing: " + organism.getName() + " - Org Size Before" + ChaosCraft.organisims.size());
        iterator.remove();

        //ChaosCraft.logger.info("Dead Orgs: " + deadOrgs.size() + " / " + ChaosCraft.organisims.size());
      }
    }

    ChaosCraft.queueSpawn(deadOrgs);

  }


}

