/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostUsernameTrainingroomsTrainingroomSessionsSessionNext"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private TrainingRoomSessionNextResponse trainingRoomSessionNextResponse;

    /**
     * @param trainingRoomSessionNextResponse
     */

    public void setTrainingRoomSessionNextResponse(TrainingRoomSessionNextResponse trainingRoomSessionNextResponse) {
        this.trainingRoomSessionNextResponse = trainingRoomSessionNextResponse;
    }

    /**
     * @return
     */

    public TrainingRoomSessionNextResponse getTrainingRoomSessionNextResponse() {
        return this.trainingRoomSessionNextResponse;
    }

    /**
     * @param trainingRoomSessionNextResponse
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult trainingRoomSessionNextResponse(
            TrainingRoomSessionNextResponse trainingRoomSessionNextResponse) {
        setTrainingRoomSessionNextResponse(trainingRoomSessionNextResponse);
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
        if (getTrainingRoomSessionNextResponse() != null)
            sb.append("TrainingRoomSessionNextResponse: ").append(getTrainingRoomSessionNextResponse());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult == false)
            return false;
        PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult other = (PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult) obj;
        if (other.getTrainingRoomSessionNextResponse() == null ^ this.getTrainingRoomSessionNextResponse() == null)
            return false;
        if (other.getTrainingRoomSessionNextResponse() != null
                && other.getTrainingRoomSessionNextResponse().equals(this.getTrainingRoomSessionNextResponse()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoomSessionNextResponse() == null) ? 0 : getTrainingRoomSessionNextResponse().hashCode());
        return hashCode;
    }

    @Override
    public PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult clone() {
        try {
            return (PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
