/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * NNetRawMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class NNetRawMarshaller {

    private static final MarshallingInfo<StructuredPojo> NNET_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("nNet").build();
    private static final MarshallingInfo<String> NNETRAW_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("nNetRaw").build();

    private static final NNetRawMarshaller instance = new NNetRawMarshaller();

    public static NNetRawMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(NNetRaw nNetRaw, ProtocolMarshaller protocolMarshaller) {

        if (nNetRaw == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(nNetRaw.getNNet(), NNET_BINDING);
            protocolMarshaller.marshall(nNetRaw.getNNetRaw(), NNETRAW_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
