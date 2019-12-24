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
 * GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult getUsernameTrainingroomsTrainingroomOrganismsOrganismResult = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomOrganismsOrganismResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomOrganismsOrganismResult.setOrganism(OrganismJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomOrganismsOrganismResult;
    }

    private static GetUsernameTrainingroomsTrainingroomOrganismsOrganismResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomOrganismsOrganismResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismResultJsonUnmarshaller();
        return instance;
    }
}
