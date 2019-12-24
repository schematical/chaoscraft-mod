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
 * PostUsernameTrainingroomsTrainingroomFitnessrulesResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller implements
        Unmarshaller<PostUsernameTrainingroomsTrainingroomFitnessrulesResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingroomsTrainingroomFitnessrulesResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingroomsTrainingroomFitnessrulesResult postUsernameTrainingroomsTrainingroomFitnessrulesResult = new PostUsernameTrainingroomsTrainingroomFitnessrulesResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingroomsTrainingroomFitnessrulesResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingroomsTrainingroomFitnessrulesResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postUsernameTrainingroomsTrainingroomFitnessrulesResult;
    }

    private static PostUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller instance;

    public static PostUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller();
        return instance;
    }
}
