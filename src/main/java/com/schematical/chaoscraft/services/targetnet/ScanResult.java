package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaosnet.model.ChaosNetException;

import java.util.ArrayList;
import java.util.Iterator;

public class ScanResult{
    private int max = 5;
    private String targetSlotId;
    private float lowestScore = 99999;
    private ArrayList<ScanEntry> scanEntries = new ArrayList<>();

    public ScanResult(String targetSlotId){
        this.targetSlotId = targetSlotId;
    }
    public ScanResult(String targetSlotId, boolean single){
        this.targetSlotId = targetSlotId;
        if(single) {
            this.max = 1;
        }
    }
    public void test(ScanEntry scanEntry){
        if(scanEntry.getScore(targetSlotId) < 0){
            return;
        }

        if(scanEntries.size() < max){
            scanEntries.add(scanEntry);
            if(scanEntry.getScore(targetSlotId) < lowestScore){
                lowestScore = scanEntry.getScore(targetSlotId);
            }
            return;
        }
        if(max == 1){
            scanEntries.clear();
            scanEntries.add(scanEntry);
            lowestScore = scanEntry.getScore(targetSlotId);
            return;
        }
        if(scanEntry.getScore(targetSlotId) > lowestScore){
            Iterator<ScanEntry> iterator = scanEntries.iterator();
            boolean removed = false;
            while(
                    iterator.hasNext() &&
                            !removed
            ){
                ScanEntry removeScanEntry = iterator.next();
                if(removeScanEntry.getScore(targetSlotId) == lowestScore){
                    iterator.remove();
                    removed = true;
                }
            }
            if(!removed){
                throw new ChaosNetException("Your math is off ");
            }
            scanEntries.add(scanEntry);
            lowestScore = scanEntry.getScore(targetSlotId);
        }
    }

    public ArrayList<ScanEntry> getTopEntities() {
        return scanEntries;
    }
}