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
 * PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestProtocolMarshaller implements
        Marshaller<Request<PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest>, PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/sessions/{session}/next").httpMethodName(HttpMethodName.POST)
            .hasExplicitPayloadMember(true).hasPayloadMembers(true).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest> marshall(
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest) {

        if (postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest> protocolMarshaller = protocolFactory
                    .createProtocolMarshaller(SDK_OPERATION_BINDING, postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest);

            protocolMarshaller.startMarshalling();
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestMarshaller.getInstance().marshall(
                    postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
