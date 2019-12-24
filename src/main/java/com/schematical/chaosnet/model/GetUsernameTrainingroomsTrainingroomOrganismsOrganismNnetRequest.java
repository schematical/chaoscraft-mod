/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

import com.amazonaws.auth.RequestSigner;
import com.amazonaws.opensdk.protect.auth.RequestSignerAware;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable,
        RequestSignerAware {

    private String organism;

    private String trainingroom;

    private String username;

    /**
     * @param organism
     */

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    /**
     * @return
     */

    public String getOrganism() {
        return this.organism;
    }

    /**
     * @param organism
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest organism(String organism) {
        setOrganism(organism);
        return this;
    }

    /**
     * @param trainingroom
     */

    public void setTrainingroom(String trainingroom) {
        this.trainingroom = trainingroom;
    }

    /**
     * @return
     */

    public String getTrainingroom() {
        return this.trainingroom;
    }

    /**
     * @param trainingroom
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest trainingroom(String trainingroom) {
        setTrainingroom(trainingroom);
        return this;
    }

    /**
     * @param username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */

    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest username(String username) {
        setUsername(username);
        return this;
    }

    /**
     * Returns a string representation of this object; useful for testing and debugging.
     *
     * @return A string representation of this object.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getOrganism() != null)
            sb.append("Organism: ").append(getOrganism()).append(",");
        if (getTrainingroom() != null)
            sb.append("Trainingroom: ").append(getTrainingroom()).append(",");
        if (getUsername() != null)
            sb.append("Username: ").append(getUsername());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest == false)
            return false;
        GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest other = (GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest) obj;
        if (other.getOrganism() == null ^ this.getOrganism() == null)
            return false;
        if (other.getOrganism() != null && other.getOrganism().equals(this.getOrganism()) == false)
            return false;
        if (other.getTrainingroom() == null ^ this.getTrainingroom() == null)
            return false;
        if (other.getTrainingroom() != null && other.getTrainingroom().equals(this.getTrainingroom()) == false)
            return false;
        if (other.getUsername() == null ^ this.getUsername() == null)
            return false;
        if (other.getUsername() != null && other.getUsername().equals(this.getUsername()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getOrganism() == null) ? 0 : getOrganism().hashCode());
        hashCode = prime * hashCode + ((getTrainingroom() == null) ? 0 : getTrainingroom().hashCode());
        hashCode = prime * hashCode + ((getUsername() == null) ? 0 : getUsername().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest clone() {
        return (GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest) super.clone();
    }

    @Override
    public Class<? extends RequestSigner> signerType() {
        return com.schematical.chaosnet.auth.ChaosnetCognitoUserPool.class;
    }

    /**
     * Set the configuration for this request.
     *
     * @param sdkRequestConfig
     *        Request configuration.
     * @return This object for method chaining.
     */
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}