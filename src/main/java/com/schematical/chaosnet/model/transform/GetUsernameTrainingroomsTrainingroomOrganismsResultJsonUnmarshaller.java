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
 * GetUsernameTrainingroomsTrainingroomOrganismsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomOrganismsResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomOrganismsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomOrganismsResult getUsernameTrainingroomsTrainingroomOrganismsResult = new GetUsernameTrainingroomsTrainingroomOrganismsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomOrganismsResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomOrganismsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomOrganismsResult;
    }

    private static GetUsernameTrainingroomsTrainingroomOrganismsResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomOrganismsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomOrganismsResultJsonUnmarshaller();
        return instance;
    }
}
