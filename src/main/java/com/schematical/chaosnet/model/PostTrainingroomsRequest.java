/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

import com.amazonaws.auth.RequestSigner;
import com.amazonaws.opensdk.protect.auth.RequestSignerAware;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostTrainingrooms"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostTrainingroomsRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable, RequestSignerAware {

    private TrainingRoom trainingRoom;

    /**
     * @param trainingRoom
     */

    public void setTrainingRoom(TrainingRoom trainingRoom) {
        this.trainingRoom = trainingRoom;
    }

    /**
     * @return
     */

    public TrainingRoom getTrainingRoom() {
        return this.trainingRoom;
    }

    /**
     * @param trainingRoom
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public PostTrainingroomsRequest trainingRoom(TrainingRoom trainingRoom) {
        setTrainingRoom(trainingRoom);
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
        if (getTrainingRoom() != null)
            sb.append("TrainingRoom: ").append(getTrainingRoom());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof PostTrainingroomsRequest == false)
            return false;
        PostTrainingroomsRequest other = (PostTrainingroomsRequest) obj;
        if (other.getTrainingRoom() == null ^ this.getTrainingRoom() == null)
            return false;
        if (other.getTrainingRoom() != null && other.getTrainingRoom().equals(this.getTrainingRoom()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoom() == null) ? 0 : getTrainingRoom().hashCode());
        return hashCode;
    }

    @Override
    public PostTrainingroomsRequest clone() {
        return (PostTrainingroomsRequest) super.clone();
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
    public PostTrainingroomsRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}
