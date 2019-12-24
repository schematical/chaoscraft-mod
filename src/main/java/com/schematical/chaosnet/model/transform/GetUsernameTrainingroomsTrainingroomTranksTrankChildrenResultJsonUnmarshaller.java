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
 * GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult getUsernameTrainingroomsTrainingroomTranksTrankChildrenResult = new GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomTranksTrankChildrenResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomTranksTrankChildrenResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomTranksTrankChildrenResult;
    }

    private static GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResultJsonUnmarshaller();
        return instance;
    }
}
