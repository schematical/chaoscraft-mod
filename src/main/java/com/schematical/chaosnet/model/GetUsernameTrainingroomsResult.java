/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingrooms"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private java.util.List<TrainingRoom> trainingRoomCollection;

    /**
     * @return
     */

    public java.util.List<TrainingRoom> getTrainingRoomCollection() {
        return trainingRoomCollection;
    }

    /**
     * @param trainingRoomCollection
     */

    public void setTrainingRoomCollection(java.util.Collection<TrainingRoom> trainingRoomCollection) {
        if (trainingRoomCollection == null) {
            this.trainingRoomCollection = null;
            return;
        }

        this.trainingRoomCollection = new java.util.ArrayList<TrainingRoom>(trainingRoomCollection);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setTrainingRoomCollection(java.util.Collection)} or
     * {@link #withTrainingRoomCollection(java.util.Collection)} if you want to override the existing values.
     * </p>
     * 
     * @param trainingRoomCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsResult trainingRoomCollection(TrainingRoom... trainingRoomCollection) {
        if (this.trainingRoomCollection == null) {
            setTrainingRoomCollection(new java.util.ArrayList<TrainingRoom>(trainingRoomCollection.length));
        }
        for (TrainingRoom ele : trainingRoomCollection) {
            this.trainingRoomCollection.add(ele);
        }
        return this;
    }

    /**
     * @param trainingRoomCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsResult trainingRoomCollection(java.util.Collection<TrainingRoom> trainingRoomCollection) {
        setTrainingRoomCollection(trainingRoomCollection);
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
        if (getTrainingRoomCollection() != null)
            sb.append("TrainingRoomCollection: ").append(getTrainingRoomCollection());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsResult == false)
            return false;
        GetUsernameTrainingroomsResult other = (GetUsernameTrainingroomsResult) obj;
        if (other.getTrainingRoomCollection() == null ^ this.getTrainingRoomCollection() == null)
            return false;
        if (other.getTrainingRoomCollection() != null && other.getTrainingRoomCollection().equals(this.getTrainingRoomCollection()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoomCollection() == null) ? 0 : getTrainingRoomCollection().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsResult clone() {
        try {
            return (GetUsernameTrainingroomsResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
