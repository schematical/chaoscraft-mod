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
 * GetUsernameResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameResultJsonUnmarshaller implements Unmarshaller<GetUsernameResult, JsonUnmarshallerContext> {

    public GetUsernameResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameResult getUsernameResult = new GetUsernameResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameResult;
    }

    private static GetUsernameResultJsonUnmarshaller instance;

    public static GetUsernameResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameResultJsonUnmarshaller();
        return instance;
    }
}
