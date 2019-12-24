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
 * GetUsernameTrainingdatasTrainingdataMediaRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetUsernameTrainingdatasTrainingdataMediaRequestProtocolMarshaller implements
        Marshaller<Request<GetUsernameTrainingdatasTrainingdataMediaRequest>, GetUsernameTrainingdatasTrainingdataMediaRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingdatas/{trainingdata}/media").httpMethodName(HttpMethodName.GET).hasExplicitPayloadMember(false)
            .hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public GetUsernameTrainingdatasTrainingdataMediaRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<GetUsernameTrainingdatasTrainingdataMediaRequest> marshall(
            GetUsernameTrainingdatasTrainingdataMediaRequest getUsernameTrainingdatasTrainingdataMediaRequest) {

        if (getUsernameTrainingdatasTrainingdataMediaRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<GetUsernameTrainingdatasTrainingdataMediaRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(
                    SDK_OPERATION_BINDING, getUsernameTrainingdatasTrainingdataMediaRequest);

            protocolMarshaller.startMarshalling();
            GetUsernameTrainingdatasTrainingdataMediaRequestMarshaller.getInstance().marshall(getUsernameTrainingdatasTrainingdataMediaRequest,
                    protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
