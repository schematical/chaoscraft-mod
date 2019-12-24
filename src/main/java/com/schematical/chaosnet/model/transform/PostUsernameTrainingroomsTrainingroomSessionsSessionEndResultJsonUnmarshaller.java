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
 * PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomSessionsSessionEndResultJsonUnmarshaller implements
        Unmarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult postUsernameTrainingroomsTrainingroomSessionsSessionEndResult = new PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingroomsTrainingroomSessionsSessionEndResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingroomsTrainingroomSessionsSessionEndResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postUsernameTrainingroomsTrainingroomSessionsSessionEndResult;
    }

    private static PostUsernameTrainingroomsTrainingroomSessionsSessionEndResultJsonUnmarshaller instance;

    public static PostUsernameTrainingroomsTrainingroomSessionsSessionEndResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingroomsTrainingroomSessionsSessionEndResultJsonUnmarshaller();
        return instance;
    }
}
