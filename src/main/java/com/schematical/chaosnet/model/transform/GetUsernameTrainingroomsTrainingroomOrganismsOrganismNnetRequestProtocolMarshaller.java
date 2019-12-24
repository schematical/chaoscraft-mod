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
 * GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequestProtocolMarshaller implements
        Marshaller<Request<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest>, GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/organisms/{organism}/nnet").httpMethodName(HttpMethodName.GET)
            .hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest> marshall(
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest) {

        if (getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest> protocolMarshaller = protocolFactory
                    .createProtocolMarshaller(SDK_OPERATION_BINDING, getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest);

            protocolMarshaller.startMarshalling();
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequestMarshaller.getInstance().marshall(
                    getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
