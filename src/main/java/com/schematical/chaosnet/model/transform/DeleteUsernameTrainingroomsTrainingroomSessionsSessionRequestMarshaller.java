/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestMarshaller {

    private static final MarshallingInfo<String> SESSION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("session").build();
    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestMarshaller instance = new DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestMarshaller();

    public static DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequest deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest,
            ProtocolMarshaller protocolMarshaller) {

        if (deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest.getSession(), SESSION_BINDING);
            protocolMarshaller.marshall(deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
