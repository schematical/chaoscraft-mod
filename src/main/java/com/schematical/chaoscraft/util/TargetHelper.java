package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.*;

import java.util.List;

public class TargetHelper {

    public TargetHelper() {

    }

    public static Double getDistDelta(Vec3d targetPos, Vec3d observerPos) {
        return targetPos.distanceTo(observerPos);
    }
    public static Double getPitchDelta(Vec3d targetPos, Vec3d observerPos, Vec3d lookVec){

        Vec3d vecToTarget = targetPos.subtract(observerPos);
        double pitch = -Math.atan2((vecToTarget.y + .5), Math.sqrt(Math.pow(vecToTarget.x, 2) + Math.pow(vecToTarget.z, 2)));
        double degrees = Math.toDegrees(pitch);

        double lookPitch = -Math.atan2(lookVec.y, Math.sqrt(Math.pow(lookVec.x, 2) + Math.pow(lookVec.z, 2)));
        double lookDeg = Math.toDegrees(lookPitch);
        degrees -= lookDeg;
        return degrees;
    }

    /**
     * This is expiremental
     * @param targetPos
     * @param observerPos
     * @param rotationPitch
     * @return
     */
    public static Double getPitchDelta(Vec3d targetPos, Vec3d observerPos, double rotationPitch){

        Vec3d vecToTarget = targetPos.subtract(observerPos);

        //double pitch = -Math.atan2((vecToTarget.y + .5), Math.sqrt(Math.pow(vecToTarget.x, 2) + Math.pow(vecToTarget.z, 2)));
        double pitch = -Math.atan2(Math.sqrt(vecToTarget.z * vecToTarget.z + vecToTarget.x * vecToTarget.x), vecToTarget.y);
        double degrees = Math.toDegrees(pitch);


        degrees -= rotationPitch;
        degrees = degrees % 360;
       /* if (degrees > 180) {
            degrees += -360;
        } else if (degrees < -180) {
            degrees += 360;
        }*/
        return degrees;
    }
    public static Double getYawDelta(Vec3d targetPos, Vec3d observerPos, double lookDeg) {

        //Vec3d lookVec = x.getEntity().getLookVec();
        Vec3d vecToTarget = targetPos.subtract(observerPos);
        double yaw = -Math.atan2(vecToTarget.x, vecToTarget.z);
        double degrees = Math.toDegrees(yaw);


        degrees -= lookDeg;
        degrees = degrees % 360;
        if (degrees > 180) {
            degrees += -360;
        } else if (degrees < -180) {
            degrees += 360;
        }
        return degrees;
    }
}