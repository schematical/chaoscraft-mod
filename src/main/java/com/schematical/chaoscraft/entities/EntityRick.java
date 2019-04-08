package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.AIFindExistingOrganisims;
import com.schematical.chaoscraft.ai.AISpawnOrganisim;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class EntityRick extends EntityLiving {

  public ForgeChunkManager.Ticket chunkTicket;

  public EntityRick(World worldIn) {
    this(worldIn, "Rick");
  }

  public EntityRick(World worldIn, String name) {
    super(worldIn);

    if (!world.isRemote) {
      if (chunkTicket == null) {
        chunkTicket = ForgeChunkManager
            .requestTicket(ChaosCraft.INSTANCE, world, ForgeChunkManager.Type.ENTITY);
        chunkTicket.bindEntity(this);
      }
    }

    this.tasks.taskEntries.clear();

    this.tasks.addTask(3, new AISpawnOrganisim(this));
    this.tasks.addTask(1, new EntityAISwimming(this));
    //this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
    this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
    //this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    this.tasks.addTask(2, new AIFindExistingOrganisims(this, EntityOrganism.class));

    this.setEntityInvulnerable(true);
    this.enablePersistence();
  }

  @Override
  public void onUpdate() {
    if (this.firstUpdate) {
      if (!this.world.isRemote) {
        ForgeChunkManager.forceChunk(chunkTicket, new ChunkPos(this.getPosition()));
      }
    }

    ChaosCraft.rickPos = this.getPosition();
    super.onUpdate();

  }

  public static class RickRenderer extends RenderLiving<EntityRick> {

    public RickRenderer(RenderManager rendermanagerIn) {
      super(rendermanagerIn, new ModelPlayer(.5f, false), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRick entity) {
      return new ResourceLocation(
          "chaoscraft:rick.png");//"minecraft:textures/entity/cow/cow.png");//ChaosCraft.MODID, "rick.png");
    }

  }

  public void onOrganisimDeath(EntityCreature creature) {
    if (!world.isRemote) {
      if (ChaosCraft.organisims.contains(creature)) {
        ChaosCraft.organisims.remove(creature);
      }
    }
  }

}