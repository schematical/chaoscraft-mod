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
 * GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult.setNNetRaw(NNetRawJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult;
    }

    private static GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResultJsonUnmarshaller();
        return instance;
    }
}
