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
 * GetSimmodelsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetSimmodelsResultJsonUnmarshaller implements Unmarshaller<GetSimmodelsResult, JsonUnmarshallerContext> {

    public GetSimmodelsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetSimmodelsResult getSimmodelsResult = new GetSimmodelsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getSimmodelsResult;
        }

        while (true) {
            if (token == null)
                break;

            getSimmodelsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getSimmodelsResult;
    }

    private static GetSimmodelsResultJsonUnmarshaller instance;

    public static GetSimmodelsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetSimmodelsResultJsonUnmarshaller();
        return instance;
    }
}
