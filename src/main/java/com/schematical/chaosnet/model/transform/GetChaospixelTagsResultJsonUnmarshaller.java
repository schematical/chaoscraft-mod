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
 * GetChaospixelTagsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetChaospixelTagsResultJsonUnmarshaller implements Unmarshaller<GetChaospixelTagsResult, JsonUnmarshallerContext> {

    public GetChaospixelTagsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetChaospixelTagsResult getChaospixelTagsResult = new GetChaospixelTagsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getChaospixelTagsResult;
        }

        while (true) {
            if (token == null)
                break;

            getChaospixelTagsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getChaospixelTagsResult;
    }

    private static GetChaospixelTagsResultJsonUnmarshaller instance;

    public static GetChaospixelTagsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetChaospixelTagsResultJsonUnmarshaller();
        return instance;
    }
}
