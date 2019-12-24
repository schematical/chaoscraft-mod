/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller {

    private static final MarshallingInfo<String> SESSION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("session").build();
    private static final MarshallingInfo<StructuredPojo> TRAININGROOMSESSIONNEXTREQUEST_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).isExplicitPayloadMember(true).build();
    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller instance = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller();

    public static PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest,
            ProtocolMarshaller protocolMarshaller) {

        if (postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest.getSession(), SESSION_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest.getTrainingRoomSessionNextRequest(),
                    TRAININGROOMSESSIONNEXTREQUEST_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
