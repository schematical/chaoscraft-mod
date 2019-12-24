/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomTranksTrank"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomTranksTrankRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable {

    private String trainingroom;

    private String trank;

    private String username;

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

    public GetUsernameTrainingroomsTrainingroomTranksTrankRequest trainingroom(String trainingroom) {
        setTrainingroom(trainingroom);
        return this;
    }

    /**
     * @param trank
     */

    public void setTrank(String trank) {
        this.trank = trank;
    }

    /**
     * @return
     */

    public String getTrank() {
        return this.trank;
    }

    /**
     * @param trank
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomTranksTrankRequest trank(String trank) {
        setTrank(trank);
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

    public GetUsernameTrainingroomsTrainingroomTranksTrankRequest username(String username) {
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
        if (getTrainingroom() != null)
            sb.append("Trainingroom: ").append(getTrainingroom()).append(",");
        if (getTrank() != null)
            sb.append("Trank: ").append(getTrank()).append(",");
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

        if (obj instanceof GetUsernameTrainingroomsTrainingroomTranksTrankRequest == false)
            return false;
        GetUsernameTrainingroomsTrainingroomTranksTrankRequest other = (GetUsernameTrainingroomsTrainingroomTranksTrankRequest) obj;
        if (other.getTrainingroom() == null ^ this.getTrainingroom() == null)
            return false;
        if (other.getTrainingroom() != null && other.getTrainingroom().equals(this.getTrainingroom()) == false)
            return false;
        if (other.getTrank() == null ^ this.getTrank() == null)
            return false;
        if (other.getTrank() != null && other.getTrank().equals(this.getTrank()) == false)
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

        hashCode = prime * hashCode + ((getTrainingroom() == null) ? 0 : getTrainingroom().hashCode());
        hashCode = prime * hashCode + ((getTrank() == null) ? 0 : getTrank().hashCode());
        hashCode = prime * hashCode + ((getUsername() == null) ? 0 : getUsername().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomTranksTrankRequest clone() {
        return (GetUsernameTrainingroomsTrainingroomTranksTrankRequest) super.clone();
    }

    /**
     * Set the configuration for this request.
     *
     * @param sdkRequestConfig
     *        Request configuration.
     * @return This object for method chaining.
     */
    public GetUsernameTrainingroomsTrainingroomTranksTrankRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}
