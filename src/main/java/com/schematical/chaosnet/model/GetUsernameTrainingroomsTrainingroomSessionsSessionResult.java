/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSession"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsSessionResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private TrainingRoomSession trainingRoomSession;

    /**
     * @param trainingRoomSession
     */

    public void setTrainingRoomSession(TrainingRoomSession trainingRoomSession) {
        this.trainingRoomSession = trainingRoomSession;
    }

    /**
     * @return
     */

    public TrainingRoomSession getTrainingRoomSession() {
        return this.trainingRoomSession;
    }

    /**
     * @param trainingRoomSession
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomSessionsSessionResult trainingRoomSession(TrainingRoomSession trainingRoomSession) {
        setTrainingRoomSession(trainingRoomSession);
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
        if (getTrainingRoomSession() != null)
            sb.append("TrainingRoomSession: ").append(getTrainingRoomSession());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomSessionsSessionResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomSessionsSessionResult other = (GetUsernameTrainingroomsTrainingroomSessionsSessionResult) obj;
        if (other.getTrainingRoomSession() == null ^ this.getTrainingRoomSession() == null)
            return false;
        if (other.getTrainingRoomSession() != null && other.getTrainingRoomSession().equals(this.getTrainingRoomSession()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTrainingRoomSession() == null) ? 0 : getTrainingRoomSession().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsSessionResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomSessionsSessionResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
