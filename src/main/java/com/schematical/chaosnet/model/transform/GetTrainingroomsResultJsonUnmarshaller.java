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
 * GetTrainingroomsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetTrainingroomsResultJsonUnmarshaller implements Unmarshaller<GetTrainingroomsResult, JsonUnmarshallerContext> {

    public GetTrainingroomsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetTrainingroomsResult getTrainingroomsResult = new GetTrainingroomsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getTrainingroomsResult;
        }

        while (true) {
            if (token == null)
                break;

            getTrainingroomsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getTrainingroomsResult;
    }

    private static GetTrainingroomsResultJsonUnmarshaller instance;

    public static GetTrainingroomsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetTrainingroomsResultJsonUnmarshaller();
        return instance;
    }
}
