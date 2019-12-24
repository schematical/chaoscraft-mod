/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessions"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private java.util.List<TrainingRoomSession> trainingRoomSessionCollection;

    /**
     * @return
     */

    public java.util.List<TrainingRoomSession> getTrainingRoomSessionCollection() {
        return trainingRoomSessionCollection;
    }

    /**
     * @param trainingRoomSessionCollection
     */

    public void setTrainingRoomSessionCollection(java.util.Collection<TrainingRoomSession> trainingRoomSessionCollection) {
        if (trainingRoomSessionCollection == null) {
            this.trainingRoomSessionCollection = null;
            return;
        }

        this.trainingRoomSessionCollection = new java.util.ArrayList<TrainingRoomSession>(trainingRoomSessionCollection);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setTrainingRoomSessionCollection(java.util.Collection)} or
     * {@link #withTrainingRoomSessionCollection(java.util.Collection)} if you want to override the existing values.
     * </p>
     * 
     * @param trainingRoomSessionCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomSessionsResult trainingRoomSessionCollection(TrainingRoomSession... trainingRoomSessionCollection) {
        if (this.trainingRoomSessionCollection == null) {
            setTrainingRoomSessionCollection(new java.util.ArrayList<TrainingRoomSession>(trainingRoomSessionCollection.length));
        }
        for (TrainingRoomSession ele : trainingRoomSessionCollection) {
            this.trainingRoomSessionCollection.add(ele);
        }
        return this;
    }

    /**
     * @param trainingRoomSessionCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomSessionsResult trainingRoomSessionCollection(
            java.util.Collection<TrainingRoomSession> trainingRoomSessionCollection) {
        setTrainingRoomSessionCollection(trainingRoomSessionCollection);
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
        if (getTrainingRoomSessionCollection() != null)
            sb.append("TrainingRoomSessionCollection: ").append(getTrainingRoomSessionCollection());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomSessionsResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomSessionsResult other = (GetUsernameTrainingroomsTrainingroomSessionsResult) obj;
        if (other.getTrainingRoomSessionCollection() == null ^ this.getTrainingRoomSessionCollection() == null)
            return false;
        if (other.getTrainingRoomSessionCollection() != null
                && other.getTrainingRoomSessionCollection().equals(this.getTrainingRoomSessionCollection()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoomSessionCollection() == null) ? 0 : getTrainingRoomSessionCollection().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomSessionsResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
