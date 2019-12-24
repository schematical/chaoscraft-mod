/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganism"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private Organism organism;

    /**
     * @param organism
     */

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    /**
     * @return
     */

    public Organism getOrganism() {
        return this.organism;
    }

    /**
     * @param organism
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult organism(Organism organism) {
        setOrganism(organism);
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
            sb.append("Organism: ").append(getOrganism());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult other = (GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult) obj;
        if (other.getOrganism() == null ^ this.getOrganism() == null)
            return false;
        if (other.getOrganism() != null && other.getOrganism().equals(this.getOrganism()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getOrganism() == null) ? 0 : getOrganism().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomOrganismsOrganismResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
