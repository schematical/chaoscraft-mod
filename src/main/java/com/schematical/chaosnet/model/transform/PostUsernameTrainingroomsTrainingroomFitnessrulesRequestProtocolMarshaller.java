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
 * PostUsernameTrainingroomsTrainingroomFitnessrulesRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomFitnessrulesRequestProtocolMarshaller implements
        Marshaller<Request<PostUsernameTrainingroomsTrainingroomFitnessrulesRequest>, PostUsernameTrainingroomsTrainingroomFitnessrulesRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/fitnessrules").httpMethodName(HttpMethodName.POST).hasExplicitPayloadMember(false)
            .hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public PostUsernameTrainingroomsTrainingroomFitnessrulesRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<PostUsernameTrainingroomsTrainingroomFitnessrulesRequest> marshall(
            PostUsernameTrainingroomsTrainingroomFitnessrulesRequest postUsernameTrainingroomsTrainingroomFitnessrulesRequest) {

        if (postUsernameTrainingroomsTrainingroomFitnessrulesRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<PostUsernameTrainingroomsTrainingroomFitnessrulesRequest> protocolMarshaller = protocolFactory
                    .createProtocolMarshaller(SDK_OPERATION_BINDING, postUsernameTrainingroomsTrainingroomFitnessrulesRequest);

            protocolMarshaller.startMarshalling();
            PostUsernameTrainingroomsTrainingroomFitnessrulesRequestMarshaller.getInstance().marshall(postUsernameTrainingroomsTrainingroomFitnessrulesRequest,
                    protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
