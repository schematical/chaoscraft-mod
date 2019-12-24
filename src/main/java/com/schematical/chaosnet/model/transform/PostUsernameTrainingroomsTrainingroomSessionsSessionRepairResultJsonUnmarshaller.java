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
 * PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResultJsonUnmarshaller implements
        Unmarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult postUsernameTrainingroomsTrainingroomSessionsSessionRepairResult = new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingroomsTrainingroomSessionsSessionRepairResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingroomsTrainingroomSessionsSessionRepairResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postUsernameTrainingroomsTrainingroomSessionsSessionRepairResult;
    }

    private static PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResultJsonUnmarshaller instance;

    public static PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResultJsonUnmarshaller();
        return instance;
    }
}
