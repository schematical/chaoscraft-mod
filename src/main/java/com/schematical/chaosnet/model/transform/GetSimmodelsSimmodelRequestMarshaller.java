/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * GetSimmodelsSimmodelRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetSimmodelsSimmodelRequestMarshaller {

    private static final MarshallingInfo<String> SIMMODEL_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("simmodel").build();

    private static final GetSimmodelsSimmodelRequestMarshaller instance = new GetSimmodelsSimmodelRequestMarshaller();

    public static GetSimmodelsSimmodelRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(GetSimmodelsSimmodelRequest getSimmodelsSimmodelRequest, ProtocolMarshaller protocolMarshaller) {

        if (getSimmodelsSimmodelRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(getSimmodelsSimmodelRequest.getSimmodel(), SIMMODEL_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
