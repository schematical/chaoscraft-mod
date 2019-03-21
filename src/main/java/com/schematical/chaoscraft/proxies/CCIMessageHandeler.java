package com.schematical.chaoscraft.proxies;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.gui.ChaosCraftGUI;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Iterator;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIMessageHandeler implements IMessageHandler<CCIMessage, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case
@SideOnly(Side.CLIENT)
    @Override public IMessage onMessage(CCIMessage message, MessageContext ctx) {
        // This is the player the packet was sent to the server from
        //EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        // The value that was sent
        JSONObject payload = message.getPayloadJSON();
        JSONArray ouputValues = (JSONArray)payload.get("outputs");
        Iterator<Object> jsonObjectIterable = ouputValues.iterator();
        ChaosCraft.topLeftMessage = payload.get("namespace").toString() + "\n";
        ChaosCraft.topLeftMessage += "Score: " + payload.get("score") + " - Age: " +  payload.get("age") + " / " + payload.get("maxAge") + "\n";
        while(jsonObjectIterable.hasNext()){
            JSONObject ouputValue = (JSONObject)jsonObjectIterable.next();
            ChaosCraft.topLeftMessage += ouputValue.get("summary") + " = " + ouputValue.get("_lastValue") + "\n";
        }
        JSONObject areaOfFocusJSON = (JSONObject)payload.get("areaOfFocus");
        Vec3d vec3d = new Vec3d(
            (double)areaOfFocusJSON.get("x"),
            (double)areaOfFocusJSON.get("y"),
            (double)areaOfFocusJSON.get("z")
        );
        int dist = Integer.parseInt(areaOfFocusJSON.get("range").toString());
        if(dist == 0){
            dist = 1;
        }

        Vec3d startVec = new Vec3d(
                vec3d.x + dist,
                vec3d.y + dist,
                vec3d.z + dist
        );
        Vec3d endVec = new Vec3d(
                vec3d.x - dist,
                vec3d.y - dist,
                vec3d.z - dist
        );

        Color color = Color.BLUE;

        ChaosCraftGUI.drawDebugBox(
           startVec,
            endVec,
            color
        );
//        ChaosCraftGUI.drawDebugLine(
//                new Vec3d(
//                        vec3d.x - dist,
//                        vec3d.y + dist,
//                        vec3d.z + dist
//                ),
//                new Vec3d(
//                        vec3d.x - dist,
//                        vec3d.y - dist,
//                        vec3d.z + dist
//                ),
//            color
//        );
//        ChaosCraftGUI.drawDebugLine(
//                new Vec3d(
//                        vec3d.x - dist,
//                        vec3d.y - dist,
//                        vec3d.z + dist
//                ),
//                new Vec3d(
//                        vec3d.x - dist,
//                        vec3d.y - dist,
//                        vec3d.z - dist
//                ),
//                color
//        );
//        ChaosCraftGUI.drawDebugLine(
//                new Vec3d(
//                        vec3d.x - dist,
//                        vec3d.y - dist,
//                        vec3d.z - dist
//                ),
//                new Vec3d(
//                        vec3d.x + dist,
//                        vec3d.y - dist,
//                        vec3d.z - dist
//                ),
//                color
//        );
//        ChaosCraftGUI.drawDebugLine(
//                new Vec3d(
//                        vec3d.x + dist,
//                        vec3d.y - dist,
//                        vec3d.z - dist
//                ),
//                new Vec3d(
//                        vec3d.x + dist,
//                        vec3d.y + dist,
//                        vec3d.z - dist
//                ),
//                color
//        );
        return null;
        //TODO: Draw Lines around the above box

    }

}