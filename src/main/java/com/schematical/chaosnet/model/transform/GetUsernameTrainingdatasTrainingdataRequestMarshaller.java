/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * GetUsernameTrainingdatasTrainingdataRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetUsernameTrainingdatasTrainingdataRequestMarshaller {

    private static final MarshallingInfo<String> TRAININGDATA_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingdata").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final GetUsernameTrainingdatasTrainingdataRequestMarshaller instance = new GetUsernameTrainingdatasTrainingdataRequestMarshaller();

    public static GetUsernameTrainingdatasTrainingdataRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(GetUsernameTrainingdatasTrainingdataRequest getUsernameTrainingdatasTrainingdataRequest, ProtocolMarshaller protocolMarshaller) {

        if (getUsernameTrainingdatasTrainingdataRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(getUsernameTrainingdatasTrainingdataRequest.getTrainingdata(), TRAININGDATA_BINDING);
            protocolMarshaller.marshall(getUsernameTrainingdatasTrainingdataRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
