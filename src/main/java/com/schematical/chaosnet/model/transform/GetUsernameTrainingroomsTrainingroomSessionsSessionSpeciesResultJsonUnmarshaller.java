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
 * GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult = new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult.setTaxonomicRankCollection(new ListUnmarshaller<TaxonomicRank>(
                    TaxonomicRankJsonUnmarshaller.getInstance()).unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult;
    }

    private static GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResultJsonUnmarshaller();
        return instance;
    }
}
