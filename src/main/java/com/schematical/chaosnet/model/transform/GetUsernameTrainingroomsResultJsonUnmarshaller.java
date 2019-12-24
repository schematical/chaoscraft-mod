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
 * GetUsernameTrainingroomsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsResultJsonUnmarshaller implements Unmarshaller<GetUsernameTrainingroomsResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsResult getUsernameTrainingroomsResult = new GetUsernameTrainingroomsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsResult.setTrainingRoomCollection(new ListUnmarshaller<TrainingRoom>(TrainingRoomJsonUnmarshaller.getInstance())
                    .unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsResult;
    }

    private static GetUsernameTrainingroomsResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsResultJsonUnmarshaller();
        return instance;
    }
}
