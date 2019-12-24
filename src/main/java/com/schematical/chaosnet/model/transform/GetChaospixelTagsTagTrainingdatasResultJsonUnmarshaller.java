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
 * GetChaospixelTagsTagTrainingdatasResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetChaospixelTagsTagTrainingdatasResultJsonUnmarshaller implements Unmarshaller<GetChaospixelTagsTagTrainingdatasResult, JsonUnmarshallerContext> {

    public GetChaospixelTagsTagTrainingdatasResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetChaospixelTagsTagTrainingdatasResult getChaospixelTagsTagTrainingdatasResult = new GetChaospixelTagsTagTrainingdatasResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getChaospixelTagsTagTrainingdatasResult;
        }

        while (true) {
            if (token == null)
                break;

            getChaospixelTagsTagTrainingdatasResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getChaospixelTagsTagTrainingdatasResult;
    }

    private static GetChaospixelTagsTagTrainingdatasResultJsonUnmarshaller instance;

    public static GetChaospixelTagsTagTrainingdatasResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetChaospixelTagsTagTrainingdatasResultJsonUnmarshaller();
        return instance;
    }
}
