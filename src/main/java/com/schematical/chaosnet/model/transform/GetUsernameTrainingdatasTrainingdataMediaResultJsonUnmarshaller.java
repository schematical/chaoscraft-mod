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
 * GetUsernameTrainingdatasTrainingdataMediaResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingdatasTrainingdataMediaResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingdatasTrainingdataMediaResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingdatasTrainingdataMediaResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingdatasTrainingdataMediaResult getUsernameTrainingdatasTrainingdataMediaResult = new GetUsernameTrainingdatasTrainingdataMediaResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingdatasTrainingdataMediaResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingdatasTrainingdataMediaResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingdatasTrainingdataMediaResult;
    }

    private static GetUsernameTrainingdatasTrainingdataMediaResultJsonUnmarshaller instance;

    public static GetUsernameTrainingdatasTrainingdataMediaResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingdatasTrainingdataMediaResultJsonUnmarshaller();
        return instance;
    }
}
