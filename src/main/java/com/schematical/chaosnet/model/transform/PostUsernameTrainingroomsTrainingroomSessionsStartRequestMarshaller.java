/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PostUsernameTrainingroomsTrainingroomSessionsStartRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsStartRequestMarshaller {

    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<StructuredPojo> TRANINGROOMSESSIONSTARTREQUEST_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).isExplicitPayloadMember(true).build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final PostUsernameTrainingroomsTrainingroomSessionsStartRequestMarshaller instance = new PostUsernameTrainingroomsTrainingroomSessionsStartRequestMarshaller();

    public static PostUsernameTrainingroomsTrainingroomSessionsStartRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(PostUsernameTrainingroomsTrainingroomSessionsStartRequest postUsernameTrainingroomsTrainingroomSessionsStartRequest,
            ProtocolMarshaller protocolMarshaller) {

        if (postUsernameTrainingroomsTrainingroomSessionsStartRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsStartRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsStartRequest.getTraningRoomSessionStartRequest(),
                    TRANINGROOMSESSIONSTARTREQUEST_BINDING);
            protocolMarshaller.marshall(postUsernameTrainingroomsTrainingroomSessionsStartRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
