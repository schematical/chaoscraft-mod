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
 * PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomSessionsSessionNextResultJsonUnmarshaller implements
        Unmarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult postUsernameTrainingroomsTrainingroomSessionsSessionNextResult = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingroomsTrainingroomSessionsSessionNextResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingroomsTrainingroomSessionsSessionNextResult.setTrainingRoomSessionNextResponse(TrainingRoomSessionNextResponseJsonUnmarshaller
                    .getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postUsernameTrainingroomsTrainingroomSessionsSessionNextResult;
    }

    private static PostUsernameTrainingroomsTrainingroomSessionsSessionNextResultJsonUnmarshaller instance;

    public static PostUsernameTrainingroomsTrainingroomSessionsSessionNextResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextResultJsonUnmarshaller();
        return instance;
    }
}
