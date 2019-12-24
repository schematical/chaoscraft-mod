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
 * GetUsernameTrainingdatasTrainingdataResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingdatasTrainingdataResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingdatasTrainingdataResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingdatasTrainingdataResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingdatasTrainingdataResult getUsernameTrainingdatasTrainingdataResult = new GetUsernameTrainingdatasTrainingdataResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingdatasTrainingdataResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingdatasTrainingdataResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingdatasTrainingdataResult;
    }

    private static GetUsernameTrainingdatasTrainingdataResultJsonUnmarshaller instance;

    public static GetUsernameTrainingdatasTrainingdataResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingdatasTrainingdataResultJsonUnmarshaller();
        return instance;
    }
}
