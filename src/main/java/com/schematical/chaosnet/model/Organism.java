/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/Organism" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class Organism implements Serializable, Cloneable, StructuredPojo {

    private Double generation;

    private NNet nNet;

    private String nNetRaw;

    private String name;

    private String namespace;

    private String ownerUsername;

    private String parentNamespace;

    private String speciesNamespace;

    private String trainingRoomNamespace;

    /**
     * @param generation
     */

    public void setGeneration(Double generation) {
        this.generation = generation;
    }

    /**
     * @return
     */

    public Double getGeneration() {
        return this.generation;
    }

    /**
     * @param generation
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism generation(Double generation) {
        setGeneration(generation);
        return this;
    }

    /**
     * @param nNet
     */

    public void setNNet(NNet nNet) {
        this.nNet = nNet;
    }

    /**
     * @return
     */

    public NNet getNNet() {
        return this.nNet;
    }

    /**
     * @param nNet
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism nNet(NNet nNet) {
        setNNet(nNet);
        return this;
    }

    /**
     * @param nNetRaw
     */

    public void setNNetRaw(String nNetRaw) {
        this.nNetRaw = nNetRaw;
    }

    /**
     * @return
     */

    public String getNNetRaw() {
        return this.nNetRaw;
    }

    /**
     * @param nNetRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism nNetRaw(String nNetRaw) {
        setNNetRaw(nNetRaw);
        return this;
    }

    /**
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */

    public String getName() {
        return this.name;
    }

    /**
     * @param name
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism name(String name) {
        setName(name);
        return this;
    }

    /**
     * @param namespace
     */

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return
     */

    public String getNamespace() {
        return this.namespace;
    }

    /**
     * @param namespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism namespace(String namespace) {
        setNamespace(namespace);
        return this;
    }

    /**
     * @param ownerUsername
     */

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * @return
     */

    public String getOwnerUsername() {
        return this.ownerUsername;
    }

    /**
     * @param ownerUsername
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism ownerUsername(String ownerUsername) {
        setOwnerUsername(ownerUsername);
        return this;
    }

    /**
     * @param parentNamespace
     */

    public void setParentNamespace(String parentNamespace) {
        this.parentNamespace = parentNamespace;
    }

    /**
     * @return
     */

    public String getParentNamespace() {
        return this.parentNamespace;
    }

    /**
     * @param parentNamespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism parentNamespace(String parentNamespace) {
        setParentNamespace(parentNamespace);
        return this;
    }

    /**
     * @param speciesNamespace
     */

    public void setSpeciesNamespace(String speciesNamespace) {
        this.speciesNamespace = speciesNamespace;
    }

    /**
     * @return
     */

    public String getSpeciesNamespace() {
        return this.speciesNamespace;
    }

    /**
     * @param speciesNamespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism speciesNamespace(String speciesNamespace) {
        setSpeciesNamespace(speciesNamespace);
        return this;
    }

    /**
     * @param trainingRoomNamespace
     */

    public void setTrainingRoomNamespace(String trainingRoomNamespace) {
        this.trainingRoomNamespace = trainingRoomNamespace;
    }

    /**
     * @return
     */

    public String getTrainingRoomNamespace() {
        return this.trainingRoomNamespace;
    }

    /**
     * @param trainingRoomNamespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Organism trainingRoomNamespace(String trainingRoomNamespace) {
        setTrainingRoomNamespace(trainingRoomNamespace);
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
        if (getGeneration() != null)
            sb.append("Generation: ").append(getGeneration()).append(",");
        if (getNNet() != null)
            sb.append("NNet: ").append(getNNet()).append(",");
        if (getNNetRaw() != null)
            sb.append("NNetRaw: ").append(getNNetRaw()).append(",");
        if (getName() != null)
            sb.append("Name: ").append(getName()).append(",");
        if (getNamespace() != null)
            sb.append("Namespace: ").append(getNamespace()).append(",");
        if (getOwnerUsername() != null)
            sb.append("OwnerUsername: ").append(getOwnerUsername()).append(",");
        if (getParentNamespace() != null)
            sb.append("ParentNamespace: ").append(getParentNamespace()).append(",");
        if (getSpeciesNamespace() != null)
            sb.append("SpeciesNamespace: ").append(getSpeciesNamespace()).append(",");
        if (getTrainingRoomNamespace() != null)
            sb.append("TrainingRoomNamespace: ").append(getTrainingRoomNamespace());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof Organism == false)
            return false;
        Organism other = (Organism) obj;
        if (other.getGeneration() == null ^ this.getGeneration() == null)
            return false;
        if (other.getGeneration() != null && other.getGeneration().equals(this.getGeneration()) == false)
            return false;
        if (other.getNNet() == null ^ this.getNNet() == null)
            return false;
        if (other.getNNet() != null && other.getNNet().equals(this.getNNet()) == false)
            return false;
        if (other.getNNetRaw() == null ^ this.getNNetRaw() == null)
            return false;
        if (other.getNNetRaw() != null && other.getNNetRaw().equals(this.getNNetRaw()) == false)
            return false;
        if (other.getName() == null ^ this.getName() == null)
            return false;
        if (other.getName() != null && other.getName().equals(this.getName()) == false)
            return false;
        if (other.getNamespace() == null ^ this.getNamespace() == null)
            return false;
        if (other.getNamespace() != null && other.getNamespace().equals(this.getNamespace()) == false)
            return false;
        if (other.getOwnerUsername() == null ^ this.getOwnerUsername() == null)
            return false;
        if (other.getOwnerUsername() != null && other.getOwnerUsername().equals(this.getOwnerUsername()) == false)
            return false;
        if (other.getParentNamespace() == null ^ this.getParentNamespace() == null)
            return false;
        if (other.getParentNamespace() != null && other.getParentNamespace().equals(this.getParentNamespace()) == false)
            return false;
        if (other.getSpeciesNamespace() == null ^ this.getSpeciesNamespace() == null)
            return false;
        if (other.getSpeciesNamespace() != null && other.getSpeciesNamespace().equals(this.getSpeciesNamespace()) == false)
            return false;
        if (other.getTrainingRoomNamespace() == null ^ this.getTrainingRoomNamespace() == null)
            return false;
        if (other.getTrainingRoomNamespace() != null && other.getTrainingRoomNamespace().equals(this.getTrainingRoomNamespace()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getGeneration() == null) ? 0 : getGeneration().hashCode());
        hashCode = prime * hashCode + ((getNNet() == null) ? 0 : getNNet().hashCode());
        hashCode = prime * hashCode + ((getNNetRaw() == null) ? 0 : getNNetRaw().hashCode());
        hashCode = prime * hashCode + ((getName() == null) ? 0 : getName().hashCode());
        hashCode = prime * hashCode + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
        hashCode = prime * hashCode + ((getOwnerUsername() == null) ? 0 : getOwnerUsername().hashCode());
        hashCode = prime * hashCode + ((getParentNamespace() == null) ? 0 : getParentNamespace().hashCode());
        hashCode = prime * hashCode + ((getSpeciesNamespace() == null) ? 0 : getSpeciesNamespace().hashCode());
        hashCode = prime * hashCode + ((getTrainingRoomNamespace() == null) ? 0 : getTrainingRoomNamespace().hashCode());
        return hashCode;
    }

    @Override
    public Organism clone() {
        try {
            return (Organism) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.OrganismMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
