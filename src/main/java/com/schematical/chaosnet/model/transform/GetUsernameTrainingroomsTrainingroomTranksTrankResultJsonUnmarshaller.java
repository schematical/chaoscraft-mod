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
 * GetUsernameTrainingroomsTrainingroomTranksTrankResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomTranksTrankResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomTranksTrankResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomTranksTrankResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomTranksTrankResult getUsernameTrainingroomsTrainingroomTranksTrankResult = new GetUsernameTrainingroomsTrainingroomTranksTrankResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomTranksTrankResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomTranksTrankResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomTranksTrankResult;
    }

    private static GetUsernameTrainingroomsTrainingroomTranksTrankResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomTranksTrankResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomTranksTrankResultJsonUnmarshaller();
        return instance;
    }
}
