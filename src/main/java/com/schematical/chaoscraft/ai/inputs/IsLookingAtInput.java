package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.gui.ChaosCraftGUI;
import com.schematical.chaoscraft.util.PositionRange;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.json.simple.JSONObject;
import scala.actors.Debug;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class IsLookingAtInput extends InputNeuron {

    public static final double MAX_DISTANCE = 3d;
    public String attributeId;
    public String attributeValue;
    public Eye eye;

    //public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        if(this.nNet.entity.getDebug()){
            //ChaosCraft.logger.info("Debugging...");
        }

        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(MAX_DISTANCE);
        if(rayTraceResult == null){
            return _lastValue;
        }
        switch(attributeId){
            case(CCAttributeId.BLOCK_ID):

                /*if(!nNet.entity.world.isRemote) {
                    ChaosCraftGUI.drawDebugLine(nNet.entity, rayTraceResult);
                }*/
                if(rayTraceResult != null) {
                    Block block = nNet.entity.world.getBlockState(
                            rayTraceResult.getBlockPos()
                    ).getBlock();

                    ResourceLocation regeistryName = block.getRegistryName();
                    String key = regeistryName.getResourceDomain() + ":" + regeistryName.getResourcePath();
                    if(attributeValue.equals(key)){
                        _lastValue = 1;
                    }
                    /*int blockId = Block.getIdFromBlock(block);
                    if (blockId == Integer.parseInt(attributeValue)) {
                        _lastValue = 1;
                    }*/
                }
            break;
            case(CCAttributeId.ENTITY_ID):

                List<EntityLiving> entities =  nNet.entity.world.getEntitiesWithinAABB(EntityLiving.class,  nNet.entity.getEntityBoundingBox().grow(MAX_DISTANCE, MAX_DISTANCE, MAX_DISTANCE));
                //





                for (EntityLiving target : entities) {
                    ResourceLocation resourceLocation = EntityRegistry.getEntry(target.getClass()).getRegistryName();
                    String key = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();
                    if(
                        key == this.attributeValue &&
                        target != nNet.entity
                    ) {


                        rayTraceResult = nNet.entity.isEntityInLineOfSight(target, 3d);
                        if (rayTraceResult != null) {
                            _lastValue = 1;
                            if (nNet.entity.getDebug()) {
                                ChaosCraft.logger.info(nNet.entity.getCCNamespace() + "IsLookingAt: " + target.getName());
                            }
                        }
                    }
                }

            break;

        }
        return _lastValue;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();


    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue;
        return response;

    }

}
