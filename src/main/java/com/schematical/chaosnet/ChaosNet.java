/**

*/
package com.schematical.chaosnet;

import javax.annotation.Generated;

import com.amazonaws.*;
import com.amazonaws.opensdk.*;
import com.amazonaws.opensdk.model.*;
import com.amazonaws.regions.*;

import com.schematical.chaosnet.model.*;

/**
 * Interface for accessing ChaosNet.
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public interface ChaosNet {

    /**
     * @param deleteUsernameTrainingroomsTrainingroomRequest
     * @return Result of the DeleteUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.DeleteUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/DeleteUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    DeleteUsernameTrainingroomsTrainingroomResult deleteUsernameTrainingroomsTrainingroom(
            DeleteUsernameTrainingroomsTrainingroomRequest deleteUsernameTrainingroomsTrainingroomRequest);

    /**
     * @param deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest
     * @return Result of the DeleteUsernameTrainingroomsTrainingroomSessionsSession operation returned by the service.
     * @sample ChaosNet.DeleteUsernameTrainingroomsTrainingroomSessionsSession
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/DeleteUsernameTrainingroomsTrainingroomSessionsSession"
     *      target="_top">AWS API Documentation</a>
     */
    DeleteUsernameTrainingroomsTrainingroomSessionsSessionResult deleteUsernameTrainingroomsTrainingroomSessionsSession(
            DeleteUsernameTrainingroomsTrainingroomSessionsSessionRequest deleteUsernameTrainingroomsTrainingroomSessionsSessionRequest);

    /**
     * @param getAuthWhoamiRequest
     * @return Result of the GetAuthWhoami operation returned by the service.
     * @sample ChaosNet.GetAuthWhoami
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetAuthWhoami"
     *      target="_top">AWS API Documentation</a>
     */
    GetAuthWhoamiResult getAuthWhoami(GetAuthWhoamiRequest getAuthWhoamiRequest);

    /**
     * @param getChaospixelTagsRequest
     * @return Result of the GetChaospixelTags operation returned by the service.
     * @sample ChaosNet.GetChaospixelTags
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetChaospixelTags"
     *      target="_top">AWS API Documentation</a>
     */
    GetChaospixelTagsResult getChaospixelTags(GetChaospixelTagsRequest getChaospixelTagsRequest);

    /**
     * @param getChaospixelTagsTagTrainingdatasRequest
     * @return Result of the GetChaospixelTagsTagTrainingdatas operation returned by the service.
     * @sample ChaosNet.GetChaospixelTagsTagTrainingdatas
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetChaospixelTagsTagTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    GetChaospixelTagsTagTrainingdatasResult getChaospixelTagsTagTrainingdatas(GetChaospixelTagsTagTrainingdatasRequest getChaospixelTagsTagTrainingdatasRequest);

    /**
     * @param getSimmodelsRequest
     * @return Result of the GetSimmodels operation returned by the service.
     * @sample ChaosNet.GetSimmodels
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetSimmodels"
     *      target="_top">AWS API Documentation</a>
     */
    GetSimmodelsResult getSimmodels(GetSimmodelsRequest getSimmodelsRequest);

    /**
     * @param getSimmodelsSimmodelRequest
     * @return Result of the GetSimmodelsSimmodel operation returned by the service.
     * @sample ChaosNet.GetSimmodelsSimmodel
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetSimmodelsSimmodel"
     *      target="_top">AWS API Documentation</a>
     */
    GetSimmodelsSimmodelResult getSimmodelsSimmodel(GetSimmodelsSimmodelRequest getSimmodelsSimmodelRequest);

    /**
     * @param getTrainingroomsRequest
     * @return Result of the GetTrainingrooms operation returned by the service.
     * @sample ChaosNet.GetTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    GetTrainingroomsResult getTrainingrooms(GetTrainingroomsRequest getTrainingroomsRequest);

    /**
     * @param getUsernameRequest
     * @return Result of the GetUsername operation returned by the service.
     * @sample ChaosNet.GetUsername
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsername" target="_top">AWS
     *      API Documentation</a>
     */
    GetUsernameResult getUsername(GetUsernameRequest getUsernameRequest);

    /**
     * @param getUsernameTrainingdatasRequest
     * @return Result of the GetUsernameTrainingdatas operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatas
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingdatasResult getUsernameTrainingdatas(GetUsernameTrainingdatasRequest getUsernameTrainingdatasRequest);

    /**
     * @param getUsernameTrainingdatasMediaRequest
     * @return Result of the GetUsernameTrainingdatasMedia operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasMedia
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasMedia"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingdatasMediaResult getUsernameTrainingdatasMedia(GetUsernameTrainingdatasMediaRequest getUsernameTrainingdatasMediaRequest);

    /**
     * @param getUsernameTrainingdatasTrainingdataRequest
     * @return Result of the GetUsernameTrainingdatasTrainingdata operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasTrainingdata
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasTrainingdata"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingdatasTrainingdataResult getUsernameTrainingdatasTrainingdata(
            GetUsernameTrainingdatasTrainingdataRequest getUsernameTrainingdatasTrainingdataRequest);

    /**
     * @param getUsernameTrainingdatasTrainingdataMediaRequest
     * @return Result of the GetUsernameTrainingdatasTrainingdataMedia operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingdatasTrainingdataMedia
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingdatasTrainingdataMedia"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingdatasTrainingdataMediaResult getUsernameTrainingdatasTrainingdataMedia(
            GetUsernameTrainingdatasTrainingdataMediaRequest getUsernameTrainingdatasTrainingdataMediaRequest);

    /**
     * @param getUsernameTrainingroomsRequest
     * @return Result of the GetUsernameTrainingrooms operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsResult getUsernameTrainingrooms(GetUsernameTrainingroomsRequest getUsernameTrainingroomsRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomResult getUsernameTrainingroomsTrainingroom(
            GetUsernameTrainingroomsTrainingroomRequest getUsernameTrainingroomsTrainingroomRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomAssetsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomAssets operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomAssets
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomAssets"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomAssetsResult getUsernameTrainingroomsTrainingroomAssets(
            GetUsernameTrainingroomsTrainingroomAssetsRequest getUsernameTrainingroomsTrainingroomAssetsRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomFitnessrulesRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomFitnessrules operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomFitnessrules
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomFitnessrules"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomFitnessrulesResult getUsernameTrainingroomsTrainingroomFitnessrules(
            GetUsernameTrainingroomsTrainingroomFitnessrulesRequest getUsernameTrainingroomsTrainingroomFitnessrulesRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganisms operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganisms
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganisms"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomOrganismsResult getUsernameTrainingroomsTrainingroomOrganisms(
            GetUsernameTrainingroomsTrainingroomOrganismsRequest getUsernameTrainingroomsTrainingroomOrganismsRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsOrganismRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganismsOrganism operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganismsOrganism
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganism"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult getUsernameTrainingroomsTrainingroomOrganismsOrganism(
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismRequest getUsernameTrainingroomsTrainingroomOrganismsOrganismRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet operation returned by the
     *         service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(
            GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest getUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessions operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessions
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessions"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomSessionsResult getUsernameTrainingroomsTrainingroomSessions(
            GetUsernameTrainingroomsTrainingroomSessionsRequest getUsernameTrainingroomsTrainingroomSessionsRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsSessionRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessionsSession operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessionsSession
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSession"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomSessionsSessionResult getUsernameTrainingroomsTrainingroomSessionsSession(
            GetUsernameTrainingroomsTrainingroomSessionsSessionRequest getUsernameTrainingroomsTrainingroomSessionsSessionRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies operation returned by the
     *         service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult getUsernameTrainingroomsTrainingroomSessionsSessionSpecies(
            GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest getUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranks operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranks
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranks"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomTranksResult getUsernameTrainingroomsTrainingroomTranks(
            GetUsernameTrainingroomsTrainingroomTranksRequest getUsernameTrainingroomsTrainingroomTranksRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksTrankRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranksTrank operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranksTrank
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranksTrank"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomTranksTrankResult getUsernameTrainingroomsTrainingroomTranksTrank(
            GetUsernameTrainingroomsTrainingroomTranksTrankRequest getUsernameTrainingroomsTrainingroomTranksTrankRequest);

    /**
     * @param getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest
     * @return Result of the GetUsernameTrainingroomsTrainingroomTranksTrankChildren operation returned by the service.
     * @sample ChaosNet.GetUsernameTrainingroomsTrainingroomTranksTrankChildren
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranksTrankChildren"
     *      target="_top">AWS API Documentation</a>
     */
    GetUsernameTrainingroomsTrainingroomTranksTrankChildrenResult getUsernameTrainingroomsTrainingroomTranksTrankChildren(
            GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest);

    /**
     * @param postAuthLoginRequest
     * @return Result of the PostAuthLogin operation returned by the service.
     * @sample ChaosNet.PostAuthLogin
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthLogin"
     *      target="_top">AWS API Documentation</a>
     */
    PostAuthLoginResult postAuthLogin(PostAuthLoginRequest postAuthLoginRequest);

    /**
     * @param postAuthSignupRequest
     * @return Result of the PostAuthSignup operation returned by the service.
     * @sample ChaosNet.PostAuthSignup
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthSignup"
     *      target="_top">AWS API Documentation</a>
     */
    PostAuthSignupResult postAuthSignup(PostAuthSignupRequest postAuthSignupRequest);

    /**
     * @param postAuthTokenRequest
     * @return Result of the PostAuthToken operation returned by the service.
     * @sample ChaosNet.PostAuthToken
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthToken"
     *      target="_top">AWS API Documentation</a>
     */
    PostAuthTokenResult postAuthToken(PostAuthTokenRequest postAuthTokenRequest);

    /**
     * @param postSimmodelsRequest
     * @return Result of the PostSimmodels operation returned by the service.
     * @sample ChaosNet.PostSimmodels
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostSimmodels"
     *      target="_top">AWS API Documentation</a>
     */
    PostSimmodelsResult postSimmodels(PostSimmodelsRequest postSimmodelsRequest);

    /**
     * @param postTrainingroomsRequest
     * @return Result of the PostTrainingrooms operation returned by the service.
     * @sample ChaosNet.PostTrainingrooms
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostTrainingrooms"
     *      target="_top">AWS API Documentation</a>
     */
    PostTrainingroomsResult postTrainingrooms(PostTrainingroomsRequest postTrainingroomsRequest);

    /**
     * @param postUsernameTrainingdatasRequest
     * @return Result of the PostUsernameTrainingdatas operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingdatas
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingdatas"
     *      target="_top">AWS API Documentation</a>
     */
    PostUsernameTrainingdatasResult postUsernameTrainingdatas(PostUsernameTrainingdatasRequest postUsernameTrainingdatasRequest);

    /**
     * @param postUsernameTrainingroomsTrainingroomFitnessrulesRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomFitnessrules operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomFitnessrules
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomFitnessrules"
     *      target="_top">AWS API Documentation</a>
     */
    PostUsernameTrainingroomsTrainingroomFitnessrulesResult postUsernameTrainingroomsTrainingroomFitnessrules(
            PostUsernameTrainingroomsTrainingroomFitnessrulesRequest postUsernameTrainingroomsTrainingroomFitnessrulesRequest);

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsSessionEnd operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsSessionEnd
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionEnd"
     *      target="_top">AWS API Documentation</a>
     */
    PostUsernameTrainingroomsTrainingroomSessionsSessionEndResult postUsernameTrainingroomsTrainingroomSessionsSessionEnd(
            PostUsernameTrainingroomsTrainingroomSessionsSessionEndRequest postUsernameTrainingroomsTrainingroomSessionsSessionEndRequest);

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
    PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult postUsernameTrainingroomsTrainingroomSessionsSessionNext(
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest postUsernameTrainingroomsTrainingroomSessionsSessionNextRequest);

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsSessionRepair operation returned by the
     *         service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsSessionRepair
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionRepair"
     *      target="_top">AWS API Documentation</a>
     */
    PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult postUsernameTrainingroomsTrainingroomSessionsSessionRepair(
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest);

    /**
     * @param postUsernameTrainingroomsTrainingroomSessionsStartRequest
     * @return Result of the PostUsernameTrainingroomsTrainingroomSessionsStart operation returned by the service.
     * @sample ChaosNet.PostUsernameTrainingroomsTrainingroomSessionsStart
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsStart"
     *      target="_top">AWS API Documentation</a>
     */
    PostUsernameTrainingroomsTrainingroomSessionsStartResult postUsernameTrainingroomsTrainingroomSessionsStart(
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest postUsernameTrainingroomsTrainingroomSessionsStartRequest);

    /**
     * @param putUsernameTrainingroomsTrainingroomRequest
     * @return Result of the PutUsernameTrainingroomsTrainingroom operation returned by the service.
     * @sample ChaosNet.PutUsernameTrainingroomsTrainingroom
     * @see <a
     *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PutUsernameTrainingroomsTrainingroom"
     *      target="_top">AWS API Documentation</a>
     */
    PutUsernameTrainingroomsTrainingroomResult putUsernameTrainingroomsTrainingroom(
            PutUsernameTrainingroomsTrainingroomRequest putUsernameTrainingroomsTrainingroomRequest);

    /**
     * @return Create new instance of builder with all defaults set.
     */
    public static ChaosNetClientBuilder builder() {
        return new ChaosNetClientBuilder();
    }

    /**
     * Shuts down this client object, releasing any resources that might be held open. This is an optional method, and
     * callers are not expected to call it, but can if they want to explicitly release any open resources. Once a client
     * has been shutdown, it should not be used to make any more requests.
     */
    void shutdown();

}
