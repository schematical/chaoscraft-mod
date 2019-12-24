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
 * DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class DeleteUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller implements
        Unmarshaller<DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult, JsonUnmarshallerContext> {

    public DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult deleteUsernameTrainingroomsTrainingroomSessionsSessionResult = new DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return deleteUsernameTrainingroomsTrainingroomSessionsSessionResult;
        }

        while (true) {
            if (token == null)
                break;

            deleteUsernameTrainingroomsTrainingroomSessionsSessionResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return deleteUsernameTrainingroomsTrainingroomSessionsSessionResult;
    }

    private static DeleteUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller instance;

    public static DeleteUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new DeleteUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller();
        return instance;
    }
}
