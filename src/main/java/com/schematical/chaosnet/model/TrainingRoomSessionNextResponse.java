/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TrainingRoomSessionNextResponse"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextResponse implements Serializable, Cloneable, StructuredPojo {

    private java.util.List<Organism> organisms;

    private java.util.List<TaxonomicRank> species;

    private Stats stats;

    /**
     * @return
     */

    public java.util.List<Organism> getOrganisms() {
        return organisms;
    }

    /**
     * @param organisms
     */

    public void setOrganisms(java.util.Collection<Organism> organisms) {
        if (organisms == null) {
            this.organisms = null;
            return;
        }

        this.organisms = new java.util.ArrayList<Organism>(organisms);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setOrganisms(java.util.Collection)} or {@link #withOrganisms(java.util.Collection)} if you want to
     * override the existing values.
     * </p>
     * 
     * @param organisms
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextResponse organisms(Organism... organisms) {
        if (this.organisms == null) {
            setOrganisms(new java.util.ArrayList<Organism>(organisms.length));
        }
        for (Organism ele : organisms) {
            this.organisms.add(ele);
        }
        return this;
    }

    /**
     * @param organisms
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextResponse organisms(java.util.Collection<Organism> organisms) {
        setOrganisms(organisms);
        return this;
    }

    /**
     * @return
     */

    public java.util.List<TaxonomicRank> getSpecies() {
        return species;
    }

    /**
     * @param species
     */

    public void setSpecies(java.util.Collection<TaxonomicRank> species) {
        if (species == null) {
            this.species = null;
            return;
        }

        this.species = new java.util.ArrayList<TaxonomicRank>(species);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setSpecies(java.util.Collection)} or {@link #withSpecies(java.util.Collection)} if you want to override
     * the existing values.
     * </p>
     * 
     * @param species
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextResponse species(TaxonomicRank... species) {
        if (this.species == null) {
            setSpecies(new java.util.ArrayList<TaxonomicRank>(species.length));
        }
        for (TaxonomicRank ele : species) {
            this.species.add(ele);
        }
        return this;
    }

    /**
     * @param species
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextResponse species(java.util.Collection<TaxonomicRank> species) {
        setSpecies(species);
        return this;
    }

    /**
     * @param stats
     */

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return
     */

    public Stats getStats() {
        return this.stats;
    }

    /**
     * @param stats
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextResponse stats(Stats stats) {
        setStats(stats);
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
        if (getOrganisms() != null)
            sb.append("Organisms: ").append(getOrganisms()).append(",");
        if (getSpecies() != null)
            sb.append("Species: ").append(getSpecies()).append(",");
        if (getStats() != null)
            sb.append("Stats: ").append(getStats());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TrainingRoomSessionNextResponse == false)
            return false;
        TrainingRoomSessionNextResponse other = (TrainingRoomSessionNextResponse) obj;
        if (other.getOrganisms() == null ^ this.getOrganisms() == null)
            return false;
        if (other.getOrganisms() != null && other.getOrganisms().equals(this.getOrganisms()) == false)
            return false;
        if (other.getSpecies() == null ^ this.getSpecies() == null)
            return false;
        if (other.getSpecies() != null && other.getSpecies().equals(this.getSpecies()) == false)
            return false;
        if (other.getStats() == null ^ this.getStats() == null)
            return false;
        if (other.getStats() != null && other.getStats().equals(this.getStats()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getOrganisms() == null) ? 0 : getOrganisms().hashCode());
        hashCode = prime * hashCode + ((getSpecies() == null) ? 0 : getSpecies().hashCode());
        hashCode = prime * hashCode + ((getStats() == null) ? 0 : getStats().hashCode());
        return hashCode;
    }

    @Override
    public TrainingRoomSessionNextResponse clone() {
        try {
            return (TrainingRoomSessionNextResponse) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TrainingRoomSessionNextResponseMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
