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
 * GetUsernameTrainingdatasResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingdatasResultJsonUnmarshaller implements Unmarshaller<GetUsernameTrainingdatasResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingdatasResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingdatasResult getUsernameTrainingdatasResult = new GetUsernameTrainingdatasResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingdatasResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingdatasResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingdatasResult;
    }

    private static GetUsernameTrainingdatasResultJsonUnmarshaller instance;

    public static GetUsernameTrainingdatasResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingdatasResultJsonUnmarshaller();
        return instance;
    }
}
