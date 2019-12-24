/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * TraningRoomSessionStartRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TraningRoomSessionStartRequestMarshaller {

    private static final MarshallingInfo<Boolean> RESET_BINDING = MarshallingInfo.builder(MarshallingType.BOOLEAN).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("reset").build();

    private static final TraningRoomSessionStartRequestMarshaller instance = new TraningRoomSessionStartRequestMarshaller();

    public static TraningRoomSessionStartRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TraningRoomSessionStartRequest traningRoomSessionStartRequest, ProtocolMarshaller protocolMarshaller) {

        if (traningRoomSessionStartRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(traningRoomSessionStartRequest.getReset(), RESET_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
