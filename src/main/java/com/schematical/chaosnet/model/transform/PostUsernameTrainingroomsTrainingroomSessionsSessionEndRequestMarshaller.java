/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller {

    private static final MarshallingInfo<String> SESSION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("session").build();
    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller instance = new PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller();

    public static PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest,
            ProtocolMarshaller protocolMarshaller) {

        if (postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest.getSession(), SESSION_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
