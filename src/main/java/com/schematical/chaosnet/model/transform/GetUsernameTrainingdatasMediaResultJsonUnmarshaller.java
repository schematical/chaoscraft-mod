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
 * GetUsernameTrainingdatasMediaResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingdatasMediaResultJsonUnmarshaller implements Unmarshaller<GetUsernameTrainingdatasMediaResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingdatasMediaResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingdatasMediaResult getUsernameTrainingdatasMediaResult = new GetUsernameTrainingdatasMediaResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingdatasMediaResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingdatasMediaResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingdatasMediaResult;
    }

    private static GetUsernameTrainingdatasMediaResultJsonUnmarshaller instance;

    public static GetUsernameTrainingdatasMediaResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingdatasMediaResultJsonUnmarshaller();
        return instance;
    }
}
