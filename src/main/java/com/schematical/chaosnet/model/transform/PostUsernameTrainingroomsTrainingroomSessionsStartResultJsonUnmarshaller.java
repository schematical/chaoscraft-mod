/**

*/
package com.schematical.chaosnet.model.transform;

import java.math.*;

import javax.annotation.Generated;

import com.schematical.chaosnet.model.*;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.*;
import com.amazonaws.transform.*;

import com.fasterxml.jackson.core.JsonToken;
import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * PostUsernameTrainingroomsTrainingroomSessionsStartResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomSessionsStartResultJsonUnmarshaller implements
        Unmarshaller<PostUsernameTrainingroomsTrainingroomSessionsStartResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingroomsTrainingroomSessionsStartResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingroomsTrainingroomSessionsStartResult postUsernameTrainingroomsTrainingroomSessionsStartResult = new PostUsernameTrainingroomsTrainingroomSessionsStartResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingroomsTrainingroomSessionsStartResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingroomsTrainingroomSessionsStartResult.setTrainingRoomSession(TrainingRoomSessionJsonUnmarshaller.getInstance().unmarshall(
                    context));
            token = context.nextToken();
        }

        return postUsernameTrainingroomsTrainingroomSessionsStartResult;
    }

    private static PostUsernameTrainingroomsTrainingroomSessionsStartResultJsonUnmarshaller instance;

    public static PostUsernameTrainingroomsTrainingroomSessionsStartResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingroomsTrainingroomSessionsStartResultJsonUnmarshaller();
        return instance;
    }
}
