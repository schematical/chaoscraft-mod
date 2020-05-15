package com.schematical.chaoscraft.ai.inputs.targetcandidate;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;


public class TCIsOwnedByInput extends InputNeuron {



        @Override
        public float evaluate(){
            ScanManager scanManager = this.getEntity().getClientOrgManager().getScanManager();
            ScanEntry scanEntry = scanManager.getFocusedScanEntry();
            if(scanEntry.getTargetBlockPos() != null){
                BlockStateMemoryBufferSlot blockStateMemoryBufferSlot = this.getEntity().getClientOrgManager().getBlockStateMemory().get(scanEntry.getTargetBlockPos());
                if(blockStateMemoryBufferSlot != null){
                    if(blockStateMemoryBufferSlot.ownerEntityId == this.getEntity().getEntityId()){
                        setCurrentValue(1);
                    }
                }

            }


            return getCurrentValue();
        }


    }
