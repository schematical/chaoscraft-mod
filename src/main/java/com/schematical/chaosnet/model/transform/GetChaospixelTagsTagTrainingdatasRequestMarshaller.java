/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * GetChaospixelTagsTagTrainingdatasRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetChaospixelTagsTagTrainingdatasRequestMarshaller {

    private static final MarshallingInfo<String> TAG_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("tag").build();

    private static final GetChaospixelTagsTagTrainingdatasRequestMarshaller instance = new GetChaospixelTagsTagTrainingdatasRequestMarshaller();

    public static GetChaospixelTagsTagTrainingdatasRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(GetChaospixelTagsTagTrainingdatasRequest getChaospixelTagsTagTrainingdatasRequest, ProtocolMarshaller protocolMarshaller) {

        if (getChaospixelTagsTagTrainingdatasRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(getChaospixelTagsTagTrainingdatasRequest.getTag(), TAG_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
