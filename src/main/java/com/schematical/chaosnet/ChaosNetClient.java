/**

*/
package com.schematical.chaosnet;

import java.net.*;
import java.util.*;

import javax.annotation.Generated;

import org.apache.commons.logging.*;

import com.amazonaws.*;
import com.amazonaws.opensdk.*;
import com.amazonaws.opensdk.model.*;
import com.amazonaws.opensdk.protect.model.transform.*;
import com.amazonaws.auth.*;
import com.amazonaws.handlers.*;
import com.amazonaws.http.*;
import com.amazonaws.internal.*;
import com.amazonaws.metrics.*;
import com.amazonaws.regions.*;
import com.amazonaws.transform.*;
import com.amazonaws.util.*;
import com.amazonaws.protocol.json.*;

import com.amazonaws.annotation.ThreadSafe;
import com.amazonaws.client.AwsSyncClientParams;

import com.amazonaws.client.ClientHandler;
import com.amazonaws.client.ClientHandlerParams;
import com.amazonaws.client.ClientExecutionParams;
import com.amazonaws.opensdk.protect.client.SdkClientHandler;
import com.amazonaws.SdkBaseException;

import com.schematical.chaosnet.model.*;
import com.schematical.chaosnet.model.transform.*;

/**
 * Client for accessing ChaosNet. All service calls made using this client are blocking, and will not return until the
 * service call completes.
 * <p>
 * 
 */
@ThreadSafe
@Generated("com.amazonaws:aws-java-sdk-code-generator")
class ChaosNetClient implements ChaosNet {

    private final ClientHandler clientHandler;

    private static final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory = new com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl(
            new JsonClientMetadata()
                    .withProtocolVersion("1.1")
                    .withSupportsCbor(false)
                    .withSupportsIon(false)
                    .withContentTypeOverride("application/json")
                    .addErrorMetadata(
                            new JsonErrorShapeMetadata().withErrorCode("BadRequestException").withModeledClass(
                                    BadRequestException.class))
                    .addErrorMetadata(
                            new JsonErrorShapeMetadata().withErrorCode("InternalServerErrorException").withModeledClass(
                                    InternalServerErrorException.class))
                    .withBaseServiceExceptionClass(ChaosNetException.class));

    /**
     * Constructs a new client to invoke service methods on ChaosNet using the specified parameters.
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not return until the service call
     * completes.
     *
     * @param clientParams
     *        Object providing client parameters.
     */
    ChaosNetClient(AwsSyncClientParams clientParams) {
        this.clientHandler = new SdkClientHandler(new ClientHandlerParams().withClientParams(clientParams));
    }

    /**
     * @param deleteUsernameTrainingroomsTrainingroomRequest
     * @return Result of the DeleteUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.DeleteUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/DeleteUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public DeleteUsernameTrainingroomsTrainingroomResult deleteUsernameTrainingroomsTrainingroom(
            DeleteUsernameTrainingroomsTrainingroomRequest deleteUsernameTrainingroomsTrainingroomRequest) {
        HttpResponseHandler<DeleteUsernameTrainingroomsTrainingroomResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new DeleteUsernameTrainingroomsTrainingroomResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<DeleteUsernameTrainingroomsTrainingroomRequest, DeleteUsernameTrainingroomsTrainingroomResult>()
                .withMarshaller(new DeleteUsernameTrainingroomsTrainingroomRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(deleteUsernameTrainingroomsTrainingroomRequest));
    }

    /**
     * @param deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest
     * @return Result of the DeleteUsernameTrainingroomsTrainingroomSessionsSession operation returned by the service.
     * @sample ChaosNet.DeleteUsernameTrainingroomsTrainingroomSessionsSession
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/DeleteUsernameTrainingroomsTrainingroomSessionsSession"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult deleteUsernameTrainingroomsTrainingroomSessionsSession(
            DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequest deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest) {
        HttpResponseHandler<DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new DeleteUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequest, DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult>()
                        .withMarshaller(new DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest));
    }

    /**
     * @param getAuthWhoamiRequest
     * @return Result of the GetAuthWhoami operation returned by the service.
     * @sample ChaosNet.GetAuthWhoami
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetAuthWhoami"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetAuthWhoamiResult getAuthWhoami(GetAuthWhoamiRequest getAuthWhoamiRequest) {
        HttpResponseHandler<GetAuthWhoamiResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new GetAuthWhoamiResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetAuthWhoamiRequest, GetAuthWhoamiResult>()
                .withMarshaller(new GetAuthWhoamiRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getAuthWhoamiRequest));
    }

    /**
     * @param getChaospixelTagsRequest
     * @return Result of the GetChaospixelTags operation returned by the service.
     * @sample ChaosNet.GetChaospixelTags
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetChaospixelTags"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetChaospixelTagsResult getChaospixelTags(GetChaospixelTagsRequest getChaospixelTagsRequest) {
        HttpResponseHandler<GetChaospixelTagsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new GetChaospixelTagsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetChaospixelTagsRequest, GetChaospixelTagsResult>()
                .withMarshaller(new GetChaospixelTagsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getChaospixelTagsRequest));
    }

    /**
     * @param getChaospixelTagsTagTrainingdatasRequest
     * @return Result of the GetChaospixelTagsTagTrainingdatas operation returned by the service.
     * @sample ChaosNet.GetChaospixelTagsTagTrainingdatas
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetChaospixelTagsTagTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetChaospixelTagsTagTrainingdatasResult getChaospixelTagsTagTrainingdatas(
            GetChaospixelTagsTagTrainingdatasRequest getChaospixelTagsTagTrainingdatasRequest) {
        HttpResponseHandler<GetChaospixelTagsTagTrainingdatasResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetChaospixelTagsTagTrainingdatasResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetChaospixelTagsTagTrainingdatasRequest, GetChaospixelTagsTagTrainingdatasResult>()
                .withMarshaller(new GetChaospixelTagsTagTrainingdatasRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getChaospixelTagsTagTrainingdatasRequest));
    }

    /**
     * @param getSimmodelsRequest
     * @return Result of the GetSimmodels operation returned by the service.
     * @sample ChaosNet.GetSimmodels
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetSimmodels"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetSimmodelsResult getSimmodels(GetSimmodelsRequest getSimmodelsRequest) {
        HttpResponseHandler<GetSimmodelsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new GetSimmodelsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetSimmodelsRequest, GetSimmodelsResult>()
                .withMarshaller(new GetSimmodelsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getSimmodelsRequest));
    }

    /**
     * @param getSimmodelsSimmodelRequest
     * @return Result of the GetSimmodelsSimmodel operation returned by the service.
     * @sample ChaosNet.GetSimmodelsSimmodel
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetSimmodelsSimmodel"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetSimmodelsSimmodelResult getSimmodelsSimmodel(GetSimmodelsSimmodelRequest getSimmodelsSimmodelRequest) {
        HttpResponseHandler<GetSimmodelsSimmodelResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetSimmodelsSimmodelResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetSimmodelsSimmodelRequest, GetSimmodelsSimmodelResult>()
                .withMarshaller(new GetSimmodelsSimmodelRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getSimmodelsSimmodelRequest));
    }

    /**
     * @param getTrainingroomsRequest
     * @return Result of the GetTrainingrooms operation returned by the service.
     * @sample ChaosNet.GetTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetTrainingroomsResult getTrainingrooms(GetTrainingroomsRequest getTrainingroomsRequest) {
        HttpResponseHandler<GetTrainingroomsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new GetTrainingroomsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetTrainingroomsRequest, GetTrainingroomsResult>()
                .withMarshaller(new GetTrainingroomsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getTrainingroomsRequest));
    }

    /**
     * @param getUsernameRequest
     * @return Result of the GetUsername operation returned by the service.
     * @sample ChaosNet.GetUsername
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsername" target="_top">AWS
     *      API Documentation</a>
     */
    @Override
    public GetUsernameResult getUsername(GetUsernameRequest getUsernameRequest) {
        HttpResponseHandler<GetUsernameResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new GetUsernameResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameRequest, GetUsernameResult>()
                .withMarshaller(new GetUsernameRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameRequest));
    }

    /**
     * @param getUsernameTrainingdatasRequest
     * @return Result of the GetUsernameTrainingdatas operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatas
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingdatasResult getUsernameTrainingdatas(GetUsernameTrainingdatasRequest getUsernameTrainingdatasRequest) {
        HttpResponseHandler<GetUsernameTrainingdatasResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetUsernameTrainingdatasResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameTrainingdatasRequest, GetUsernameTrainingdatasResult>()
                .withMarshaller(new GetUsernameTrainingdatasRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameTrainingdatasRequest));
    }

    /**
     * @param getUsernameTrainingdatasMediaRequest
     * @return Result of the GetUsernameTrainingdatasMedia operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasMedia
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasMedia"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingdatasMediaResult getUsernameTrainingdatasMedia(GetUsernameTrainingdatasMediaRequest getUsernameTrainingdatasMediaRequest) {
        HttpResponseHandler<GetUsernameTrainingdatasMediaResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetUsernameTrainingdatasMediaResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameTrainingdatasMediaRequest, GetUsernameTrainingdatasMediaResult>()
                .withMarshaller(new GetUsernameTrainingdatasMediaRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameTrainingdatasMediaRequest));
    }

    /**
     * @param getUsernameTrainingdatasTrainingdataRequest
     * @return Result of the GetUsernameTrainingdatasTrainingdata operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasTrainingdata
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasTrainingdata"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingdatasTrainingdataResult getUsernameTrainingdatasTrainingdata(
            GetUsernameTrainingdatasTrainingdataRequest getUsernameTrainingdatasTrainingdataRequest) {
        HttpResponseHandler<GetUsernameTrainingdatasTrainingdataResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetUsernameTrainingdatasTrainingdataResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameTrainingdatasTrainingdataRequest, GetUsernameTrainingdatasTrainingdataResult>()
                .withMarshaller(new GetUsernameTrainingdatasTrainingdataRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameTrainingdatasTrainingdataRequest));
    }

    /**
     * @param getUsernameTrainingdatasTrainingdataMediaRequest
     * @return Result of the GetUsernameTrainingdatasTrainingdataMedia operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasTrainingdataMedia
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasTrainingdataMedia"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingdatasTrainingdataMediaResult getUsernameTrainingdatasTrainingdataMedia(
            GetUsernameTrainingdatasTrainingdataMediaRequest getUsernameTrainingdatasTrainingdataMediaRequest) {
        HttpResponseHandler<GetUsernameTrainingdatasTrainingdataMediaResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingdatasTrainingdataMediaResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingdatasTrainingdataMediaRequest, GetUsernameTrainingdatasTrainingdataMediaResult>()
                        .withMarshaller(new GetUsernameTrainingdatasTrainingdataMediaRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingdatasTrainingdataMediaRequest));
    }

    /**
     * @param getUsernameTrainingroomsRequest
     * @return Result of the GetUsernameTrainingrooms operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsResult getUsernameTrainingrooms(GetUsernameTrainingroomsRequest getUsernameTrainingroomsRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetUsernameTrainingroomsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameTrainingroomsRequest, GetUsernameTrainingroomsResult>()
                .withMarshaller(new GetUsernameTrainingroomsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameTrainingroomsRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomResult getUsernameTrainingroomsTrainingroom(
            GetUsernameTrainingroomsTrainingroomRequest getUsernameTrainingroomsTrainingroomRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new GetUsernameTrainingroomsTrainingroomResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomRequest, GetUsernameTrainingroomsTrainingroomResult>()
                .withMarshaller(new GetUsernameTrainingroomsTrainingroomRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(getUsernameTrainingroomsTrainingroomRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomAssetsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomAssets operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomAssets
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomAssets"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomAssetsResult getUsernameTrainingroomsTrainingroomAssets(
            GetUsernameTrainingroomsTrainingroomAssetsRequest getUsernameTrainingroomsTrainingroomAssetsRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomAssetsResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomAssetsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomAssetsRequest, GetUsernameTrainingroomsTrainingroomAssetsResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomAssetsRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomAssetsRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomFitnessrulesRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomFitnessrules operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomFitnessrules
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomFitnessrules"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomFitnessrulesResult getUsernameTrainingroomsTrainingroomFitnessrules(
            GetUsernameTrainingroomsTrainingroomFitnessrulesRequest getUsernameTrainingroomsTrainingroomFitnessrulesRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomFitnessrulesResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomFitnessrulesRequest, GetUsernameTrainingroomsTrainingroomFitnessrulesResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomFitnessrulesRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomFitnessrulesRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganisms operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganisms
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganisms"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsResult getUsernameTrainingroomsTrainingroomOrganisms(
            GetUsernameTrainingroomsTrainingroomOrganismsRequest getUsernameTrainingroomsTrainingroomOrganismsRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomOrganismsResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomOrganismsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomOrganismsRequest, GetUsernameTrainingroomsTrainingroomOrganismsResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomOrganismsRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomOrganismsRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsOrganismRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganismsOrganism operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganismsOrganism
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganism"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult getUsernameTrainingroomsTrainingroomOrganismsOrganism(
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismRequest getUsernameTrainingroomsTrainingroomOrganismsOrganismRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomOrganismsOrganismResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomOrganismsOrganismRequest, GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomOrganismsOrganismRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomOrganismsOrganismRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet operation returned by the
     *         service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest, GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessions operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessions
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessions"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsResult getUsernameTrainingroomsTrainingroomSessions(
            GetUsernameTrainingroomsTrainingroomSessionsRequest getUsernameTrainingroomsTrainingroomSessionsRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomSessionsResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomSessionsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomSessionsRequest, GetUsernameTrainingroomsTrainingroomSessionsResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomSessionsRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomSessionsRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsSessionRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessionsSession operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessionsSession
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSession"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsSessionResult getUsernameTrainingroomsTrainingroomSessionsSession(
            GetUsernameTrainingroomsTrainingroomSessionsSessionRequest getUsernameTrainingroomsTrainingroomSessionsSessionRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomSessionsSessionResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomSessionsSessionResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomSessionsSessionRequest, GetUsernameTrainingroomsTrainingroomSessionsSessionResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomSessionsSessionRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomSessionsSessionRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies operation returned by the
     *         service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult getUsernameTrainingroomsTrainingroomSessionsSessionSpecies(
            GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest, GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranks operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranks
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranks"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomTranksResult getUsernameTrainingroomsTrainingroomTranks(
            GetUsernameTrainingroomsTrainingroomTranksRequest getUsernameTrainingroomsTrainingroomTranksRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomTranksResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomTranksResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomTranksRequest, GetUsernameTrainingroomsTrainingroomTranksResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomTranksRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomTranksRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksTrankRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranksTrank operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranksTrank
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranksTrank"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomTranksTrankResult getUsernameTrainingroomsTrainingroomTranksTrank(
            GetUsernameTrainingroomsTrainingroomTranksTrankRequest getUsernameTrainingroomsTrainingroomTranksTrankRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomTranksTrankResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomTranksTrankResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomTranksTrankRequest, GetUsernameTrainingroomsTrainingroomTranksTrankResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomTranksTrankRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomTranksTrankRequest));
    }

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranksTrankChildren operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranksTrankChildren
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranksTrankChildren"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult getUsernameTrainingroomsTrainingroomTranksTrankChildren(
            GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest) {
        HttpResponseHandler<GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest, GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult>()
                        .withMarshaller(new GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest));
    }

    /**
     * @param postAuthLoginRequest
     * @return Result of the PostAuthLogin operation returned by the service.
     * @sample ChaosNet.PostAuthLogin
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthLogin"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostAuthLoginResult postAuthLogin(PostAuthLoginRequest postAuthLoginRequest) {
        HttpResponseHandler<PostAuthLoginResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new PostAuthLoginResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostAuthLoginRequest, PostAuthLoginResult>()
                .withMarshaller(new PostAuthLoginRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postAuthLoginRequest));
    }

    /**
     * @param postAuthSignupRequest
     * @return Result of the PostAuthSignup operation returned by the service.
     * @sample ChaosNet.PostAuthSignup
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthSignup"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostAuthSignupResult postAuthSignup(PostAuthSignupRequest postAuthSignupRequest) {
        HttpResponseHandler<PostAuthSignupResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new PostAuthSignupResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostAuthSignupRequest, PostAuthSignupResult>()
                .withMarshaller(new PostAuthSignupRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postAuthSignupRequest));
    }

    /**
     * @param postAuthTokenRequest
     * @return Result of the PostAuthToken operation returned by the service.
     * @sample ChaosNet.PostAuthToken
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthToken"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostAuthTokenResult postAuthToken(PostAuthTokenRequest postAuthTokenRequest) {
        HttpResponseHandler<PostAuthTokenResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new PostAuthTokenResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostAuthTokenRequest, PostAuthTokenResult>()
                .withMarshaller(new PostAuthTokenRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postAuthTokenRequest));
    }

    /**
     * @param postSimmodelsRequest
     * @return Result of the PostSimmodels operation returned by the service.
     * @sample ChaosNet.PostSimmodels
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostSimmodels"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostSimmodelsResult postSimmodels(PostSimmodelsRequest postSimmodelsRequest) {
        HttpResponseHandler<PostSimmodelsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new PostSimmodelsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostSimmodelsRequest, PostSimmodelsResult>()
                .withMarshaller(new PostSimmodelsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postSimmodelsRequest));
    }

    /**
     * @param postTrainingroomsRequest
     * @return Result of the PostTrainingrooms operation returned by the service.
     * @sample ChaosNet.PostTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostTrainingroomsResult postTrainingrooms(PostTrainingroomsRequest postTrainingroomsRequest) {
        HttpResponseHandler<PostTrainingroomsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
                .withHasStreamingSuccessResponse(false), new PostTrainingroomsResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostTrainingroomsRequest, PostTrainingroomsResult>()
                .withMarshaller(new PostTrainingroomsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postTrainingroomsRequest));
    }

    /**
     * @param postUsernameTrainingdatasRequest
     * @return Result of the PostUsernameTrainingdatas operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingdatas
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingdatasResult postUsernameTrainingdatas(PostUsernameTrainingdatasRequest postUsernameTrainingdatasRequest) {
        HttpResponseHandler<PostUsernameTrainingdatasResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new PostUsernameTrainingdatasResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PostUsernameTrainingdatasRequest, PostUsernameTrainingdatasResult>()
                .withMarshaller(new PostUsernameTrainingdatasRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(postUsernameTrainingdatasRequest));
    }

    /**
     * @param postUsernameTrainingroomsTrainingroomFitnessrulesRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomFitnessrules operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomFitnessrules
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomFitnessrules"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingroomsTrainingroomFitnessrulesResult postUsernameTrainingroomsTrainingroomFitnessrules(
            PostUsernameTrainingroomsTrainingroomFitnessrulesRequest postUsernameTrainingroomsTrainingroomFitnessrulesRequest) {
        HttpResponseHandler<PostUsernameTrainingroomsTrainingroomFitnessrulesResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new PostUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<PostUsernameTrainingroomsTrainingroomFitnessrulesRequest, PostUsernameTrainingroomsTrainingroomFitnessrulesResult>()
                        .withMarshaller(new PostUsernameTrainingroomsTrainingroomFitnessrulesRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(postUsernameTrainingroomsTrainingroomFitnessrulesRequest));
    }

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsSessionEnd operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsSessionEnd
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionEnd"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult postUsernameTrainingroomsTrainingroomSessionsSessionEnd(
            PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest) {
        HttpResponseHandler<PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new PostUsernameTrainingroomsTrainingroomSessionsSessionEndResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest, PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult>()
                        .withMarshaller(new PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest));
    }

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsSessionNext operation returned by the service.
     * @throws BadRequestException
     * @throws InternalServerErrorException
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsSessionNext
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionNext"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult postUsernameTrainingroomsTrainingroomSessionsSessionNext(
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest) {
        HttpResponseHandler<PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new PostUsernameTrainingroomsTrainingroomSessionsSessionNextResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
                new JsonErrorShapeMetadata().withModeledClass(BadRequestException.class).withHttpStatusCode(400), new JsonErrorShapeMetadata()
                        .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500));

        return clientHandler
                .execute(new ClientExecutionParams<PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest, PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult>()
                        .withMarshaller(new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest));
    }

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsSessionRepair operation returned by the
     *         service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsSessionRepair
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionRepair"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult postUsernameTrainingroomsTrainingroomSessionsSessionRepair(
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest) {
        HttpResponseHandler<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest, PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult>()
                        .withMarshaller(new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest));
    }

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsStartRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsStart operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsStart
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsStart"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PostUsernameTrainingroomsTrainingroomSessionsStartResult postUsernameTrainingroomsTrainingroomSessionsStart(
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest postUsernameTrainingroomsTrainingroomSessionsStartRequest) {
        HttpResponseHandler<PostUsernameTrainingroomsTrainingroomSessionsStartResult> responseHandler = protocolFactory.createResponseHandler(
                new JsonOperationMetadata().withPayloadJson(true).withHasStreamingSuccessResponse(false),
                new PostUsernameTrainingroomsTrainingroomSessionsStartResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler
                .execute(new ClientExecutionParams<PostUsernameTrainingroomsTrainingroomSessionsStartRequest, PostUsernameTrainingroomsTrainingroomSessionsStartResult>()
                        .withMarshaller(new PostUsernameTrainingroomsTrainingroomSessionsStartRequestProtocolMarshaller(protocolFactory))
                        .withResponseHandler(responseHandler).withErrorResponseHandler(errorResponseHandler)
                        .withInput(postUsernameTrainingroomsTrainingroomSessionsStartRequest));
    }

    /**
     * @param putUsernameTrainingroomsTrainingroomRequest
     * @return Result of the PutUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.PutUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PutUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    @Override
    public PutUsernameTrainingroomsTrainingroomResult putUsernameTrainingroomsTrainingroom(
            PutUsernameTrainingroomsTrainingroomRequest putUsernameTrainingroomsTrainingroomRequest) {
        HttpResponseHandler<PutUsernameTrainingroomsTrainingroomResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata()
                .withPayloadJson(true).withHasStreamingSuccessResponse(false), new PutUsernameTrainingroomsTrainingroomResultJsonUnmarshaller());

        HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler();

        return clientHandler.execute(new ClientExecutionParams<PutUsernameTrainingroomsTrainingroomRequest, PutUsernameTrainingroomsTrainingroomResult>()
                .withMarshaller(new PutUsernameTrainingroomsTrainingroomRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
                .withErrorResponseHandler(errorResponseHandler).withInput(putUsernameTrainingroomsTrainingroomRequest));
    }

    /**
     * Create the error response handler for the operation.
     * 
     * @param errorShapeMetadata
     *        Error metadata for the given operation
     * @return Configured error response handler to pass to HTTP layer
     */
    private HttpResponseHandler<SdkBaseException> createErrorResponseHandler(JsonErrorShapeMetadata... errorShapeMetadata) {
        return protocolFactory.createErrorResponseHandler(new JsonErrorResponseMetadata().withErrorShapes(Arrays.asList(errorShapeMetadata)));
    }

    @Override
    public void shutdown() {
        clientHandler.shutdown();
    }

}
