package com.schematical.chaoscraft.ai.inputs.targetcandidate;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.json.simple.JSONObject;


public class TCPosIsRelativeInput extends InputNeuron {

    private Vec3i offset;
    private String attributeId;
    private String attributeValue;

    @Override
    public float evaluate(){
        ScanManager scanManager = this.getEntity().getClientOrgManager().getScanManager();
        ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        if(scanEntry.getTargetBlockPos() == null) {
            return getCurrentValue();
        }
        BlockPos offsetBlockPos = new BlockPos(
                offset.getX() + scanEntry.getPosition().getX(),
                offset.getY() + scanEntry.getPosition().getY(),
                offset.getZ() + scanEntry.getPosition().getZ()
        );
        ChaosTarget offsetTarget = new ChaosTarget(offsetBlockPos);
        switch(attributeId) {
            case (CCAttributeId.BLOCK_ID):
                if (
                    offsetTarget.getObservedAttributes(getEntity()).resourceId.equals(attributeValue)
                ) {
                    setCurrentValue(1);
                }
                break;
            case (CCAttributeId.ENTITY_ID):
                throw new ChaosNetException("This prob should not happen");
               /* if (
                        scanEntry.atts.resourceId.equals(attributeValue)
                ) {
                    setCurrentValue(1);
                }*/
               // break;
            case (CCAttributeId.OWNER_ENTITY):


                //BlockState blockState = getEntity().world.getBlockState(offsetBlockPos);
                if (attributeValue.equals("me")) {
                    BlockStateMemoryBufferSlot blockStateMemoryBufferSlot = this.getEntity().getClientOrgManager().getBlockStateMemory().get(offsetBlockPos);
                    if (blockStateMemoryBufferSlot != null) {
                        if (blockStateMemoryBufferSlot.ownerEntityId == this.getEntity().getEntityId()) {
                            setCurrentValue(1);
                        }
                    }
                } else {
                    throw new ChaosNetException("TODO: Write me");
                }
                break;
        }





        return getCurrentValue();
    }
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        try {
            attributeId = jsonObject.get("attributeId").toString();
            attributeValue = jsonObject.get("attributeValue").toString();
            JSONObject pos = (JSONObject)jsonObject.get("pos");

            offset = new Vec3i(
                    Integer.parseInt(pos.get("X").toString()),
                    Integer.parseInt(pos.get("Y").toString()),
                    Integer.parseInt(pos.get("Z").toString())
            );
        }catch(Exception e){
            offset = new Vec3i(0,0,0);
            throw new ChaosNetException("!!!!!!!!!!!!!!! MISSING POS!!!!!!!!!!!! \n" + jsonObject.toJSONString());//throw e;
        }

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue + "=> " + this.getPrettyCurrValue();
        return response;

    }


    }
