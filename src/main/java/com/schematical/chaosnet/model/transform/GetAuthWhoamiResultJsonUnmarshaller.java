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
 * GetAuthWhoamiResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetAuthWhoamiResultJsonUnmarshaller implements Unmarshaller<GetAuthWhoamiResult, JsonUnmarshallerContext> {

    public GetAuthWhoamiResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetAuthWhoamiResult getAuthWhoamiResult = new GetAuthWhoamiResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getAuthWhoamiResult;
        }

        while (true) {
            if (token == null)
                break;

            getAuthWhoamiResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getAuthWhoamiResult;
    }

    private static GetAuthWhoamiResultJsonUnmarshaller instance;

    public static GetAuthWhoamiResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetAuthWhoamiResultJsonUnmarshaller();
        return instance;
    }
}
