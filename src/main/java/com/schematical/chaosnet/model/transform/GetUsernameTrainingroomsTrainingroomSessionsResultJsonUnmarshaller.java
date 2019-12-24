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
 * GetUsernameTrainingroomsTrainingroomSessionsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomSessionsResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomSessionsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomSessionsResult getUsernameTrainingroomsTrainingroomSessionsResult = new GetUsernameTrainingroomsTrainingroomSessionsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomSessionsResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomSessionsResult.setTrainingRoomSessionCollection(new ListUnmarshaller<TrainingRoomSession>(
                    TrainingRoomSessionJsonUnmarshaller.getInstance()).unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomSessionsResult;
    }

    private static GetUsernameTrainingroomsTrainingroomSessionsResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomSessionsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomSessionsResultJsonUnmarshaller();
        return instance;
    }
}
