/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.amazonaws.Request;

import com.amazonaws.http.HttpMethodName;
import com.schematical.chaosnet.model.*;
import com.amazonaws.transform.Marshaller;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestProtocolMarshaller implements
        Marshaller<Request<PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest>, PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/sessions/{session}/end").httpMethodName(HttpMethodName.POST)
            .hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest> marshall(
            PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest) {

        if (postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest> protocolMarshaller = protocolFactory
                    .createProtocolMarshaller(SDK_OPERATION_BINDING, postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest);

            protocolMarshaller.startMarshalling();
            PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestMarshaller.getInstance().marshall(
                    postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
