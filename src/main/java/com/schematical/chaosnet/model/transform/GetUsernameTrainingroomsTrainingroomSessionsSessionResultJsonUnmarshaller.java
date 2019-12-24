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
 * GetUsernameTrainingroomsTrainingroomSessionsSessionResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomSessionsSessionResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomSessionsSessionResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomSessionsSessionResult getUsernameTrainingroomsTrainingroomSessionsSessionResult = new GetUsernameTrainingroomsTrainingroomSessionsSessionResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomSessionsSessionResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomSessionsSessionResult.setTrainingRoomSession(TrainingRoomSessionJsonUnmarshaller.getInstance().unmarshall(
                    context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomSessionsSessionResult;
    }

    private static GetUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller();
        return instance;
    }
}
