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
 * PutUsernameTrainingroomsTrainingroomResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PutUsernameTrainingroomsTrainingroomResultJsonUnmarshaller implements
        Unmarshaller<PutUsernameTrainingroomsTrainingroomResult, JsonUnmarshallerContext> {

    public PutUsernameTrainingroomsTrainingroomResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PutUsernameTrainingroomsTrainingroomResult putUsernameTrainingroomsTrainingroomResult = new PutUsernameTrainingroomsTrainingroomResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return putUsernameTrainingroomsTrainingroomResult;
        }

        while (true) {
            if (token == null)
                break;

            putUsernameTrainingroomsTrainingroomResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return putUsernameTrainingroomsTrainingroomResult;
    }

    private static PutUsernameTrainingroomsTrainingroomResultJsonUnmarshaller instance;

    public static PutUsernameTrainingroomsTrainingroomResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PutUsernameTrainingroomsTrainingroomResultJsonUnmarshaller();
        return instance;
    }
}
