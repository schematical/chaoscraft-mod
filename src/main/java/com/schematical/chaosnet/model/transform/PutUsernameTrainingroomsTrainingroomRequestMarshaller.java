/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PutUsernameTrainingroomsTrainingroomRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PutUsernameTrainingroomsTrainingroomRequestMarshaller {

    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final PutUsernameTrainingroomsTrainingroomRequestMarshaller instance = new PutUsernameTrainingroomsTrainingroomRequestMarshaller();

    public static PutUsernameTrainingroomsTrainingroomRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(PutUsernameTrainingroomsTrainingroomRequest putUsernameTrainingroomsTrainingroomRequest, ProtocolMarshaller protocolMarshaller) {

        if (putUsernameTrainingroomsTrainingroomRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(putUsernameTrainingroomsTrainingroomRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(putUsernameTrainingroomsTrainingroomRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
