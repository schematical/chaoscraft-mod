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
 * GetUsernameTrainingroomsTrainingroomTranksResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomTranksResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomTranksResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomTranksResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomTranksResult getUsernameTrainingroomsTrainingroomTranksResult = new GetUsernameTrainingroomsTrainingroomTranksResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomTranksResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomTranksResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomTranksResult;
    }

    private static GetUsernameTrainingroomsTrainingroomTranksResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomTranksResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomTranksResultJsonUnmarshaller();
        return instance;
    }
}
