/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomFitnessrules"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomFitnessrulesResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private TrainingRoomFitnessRules trainingRoomFitnessRules;

    /**
     * @param trainingRoomFitnessRules
     */

    public void setTrainingRoomFitnessRules(TrainingRoomFitnessRules trainingRoomFitnessRules) {
        this.trainingRoomFitnessRules = trainingRoomFitnessRules;
    }

    /**
     * @return
     */

    public TrainingRoomFitnessRules getTrainingRoomFitnessRules() {
        return this.trainingRoomFitnessRules;
    }

    /**
     * @param trainingRoomFitnessRules
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomFitnessrulesResult trainingRoomFitnessRules(TrainingRoomFitnessRules trainingRoomFitnessRules) {
        setTrainingRoomFitnessRules(trainingRoomFitnessRules);
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
        if (getTrainingRoomFitnessRules() != null)
            sb.append("TrainingRoomFitnessRules: ").append(getTrainingRoomFitnessRules());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomFitnessrulesResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomFitnessrulesResult other = (GetUsernameTrainingroomsTrainingroomFitnessrulesResult) obj;
        if (other.getTrainingRoomFitnessRules() == null ^ this.getTrainingRoomFitnessRules() == null)
            return false;
        if (other.getTrainingRoomFitnessRules() != null && other.getTrainingRoomFitnessRules().equals(this.getTrainingRoomFitnessRules()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoomFitnessRules() == null) ? 0 : getTrainingRoomFitnessRules().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomFitnessrulesResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomFitnessrulesResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
