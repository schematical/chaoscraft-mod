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
 * DeleteUsernameTrainingroomsTrainingroomResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class DeleteUsernameTrainingroomsTrainingroomResultJsonUnmarshaller implements
        Unmarshaller<DeleteUsernameTrainingroomsTrainingroomResult, JsonUnmarshallerContext> {

    public DeleteUsernameTrainingroomsTrainingroomResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteUsernameTrainingroomsTrainingroomResult deleteUsernameTrainingroomsTrainingroomResult = new DeleteUsernameTrainingroomsTrainingroomResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return deleteUsernameTrainingroomsTrainingroomResult;
        }

        while (true) {
            if (token == null)
                break;

            deleteUsernameTrainingroomsTrainingroomResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return deleteUsernameTrainingroomsTrainingroomResult;
    }

    private static DeleteUsernameTrainingroomsTrainingroomResultJsonUnmarshaller instance;

    public static DeleteUsernameTrainingroomsTrainingroomResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new DeleteUsernameTrainingroomsTrainingroomResultJsonUnmarshaller();
        return instance;
    }
}
