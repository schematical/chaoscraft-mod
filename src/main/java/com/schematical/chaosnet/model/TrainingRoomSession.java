/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TrainingRoomSession"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSession implements Serializable, Cloneable, StructuredPojo {

    private String genomeNamespace;

    private String name;

    private String namespace;

    private java.util.List<String> organisms;

    private String ownerUsername;

    private Double ttl;

    /**
     * @param genomeNamespace
     */

    public void setGenomeNamespace(String genomeNamespace) {
        this.genomeNamespace = genomeNamespace;
    }

    /**
     * @return
     */

    public String getGenomeNamespace() {
        return this.genomeNamespace;
    }

    /**
     * @param genomeNamespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSession genomeNamespace(String genomeNamespace) {
        setGenomeNamespace(genomeNamespace);
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

    public TrainingRoomSession name(String name) {
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

    public TrainingRoomSession namespace(String namespace) {
        setNamespace(namespace);
        return this;
    }

    /**
     * @return
     */

    public java.util.List<String> getOrganisms() {
        return organisms;
    }

    /**
     * @param organisms
     */

    public void setOrganisms(java.util.Collection<String> organisms) {
        if (organisms == null) {
            this.organisms = null;
            return;
        }

        this.organisms = new java.util.ArrayList<String>(organisms);
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

    public TrainingRoomSession organisms(String... organisms) {
        if (this.organisms == null) {
            setOrganisms(new java.util.ArrayList<String>(organisms.length));
        }
        for (String ele : organisms) {
            this.organisms.add(ele);
        }
        return this;
    }

    /**
     * @param organisms
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSession organisms(java.util.Collection<String> organisms) {
        setOrganisms(organisms);
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

    public TrainingRoomSession ownerUsername(String ownerUsername) {
        setOwnerUsername(ownerUsername);
        return this;
    }

    /**
     * @param ttl
     */

    public void setTtl(Double ttl) {
        this.ttl = ttl;
    }

    /**
     * @return
     */

    public Double getTtl() {
        return this.ttl;
    }

    /**
     * @param ttl
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSession ttl(Double ttl) {
        setTtl(ttl);
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
        if (getGenomeNamespace() != null)
            sb.append("GenomeNamespace: ").append(getGenomeNamespace()).append(",");
        if (getName() != null)
            sb.append("Name: ").append(getName()).append(",");
        if (getNamespace() != null)
            sb.append("Namespace: ").append(getNamespace()).append(",");
        if (getOrganisms() != null)
            sb.append("Organisms: ").append(getOrganisms()).append(",");
        if (getOwnerUsername() != null)
            sb.append("OwnerUsername: ").append(getOwnerUsername()).append(",");
        if (getTtl() != null)
            sb.append("Ttl: ").append(getTtl());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TrainingRoomSession == false)
            return false;
        TrainingRoomSession other = (TrainingRoomSession) obj;
        if (other.getGenomeNamespace() == null ^ this.getGenomeNamespace() == null)
            return false;
        if (other.getGenomeNamespace() != null && other.getGenomeNamespace().equals(this.getGenomeNamespace()) == false)
            return false;
        if (other.getName() == null ^ this.getName() == null)
            return false;
        if (other.getName() != null && other.getName().equals(this.getName()) == false)
            return false;
        if (other.getNamespace() == null ^ this.getNamespace() == null)
            return false;
        if (other.getNamespace() != null && other.getNamespace().equals(this.getNamespace()) == false)
            return false;
        if (other.getOrganisms() == null ^ this.getOrganisms() == null)
            return false;
        if (other.getOrganisms() != null && other.getOrganisms().equals(this.getOrganisms()) == false)
            return false;
        if (other.getOwnerUsername() == null ^ this.getOwnerUsername() == null)
            return false;
        if (other.getOwnerUsername() != null && other.getOwnerUsername().equals(this.getOwnerUsername()) == false)
            return false;
        if (other.getTtl() == null ^ this.getTtl() == null)
            return false;
        if (other.getTtl() != null && other.getTtl().equals(this.getTtl()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getGenomeNamespace() == null) ? 0 : getGenomeNamespace().hashCode());
        hashCode = prime * hashCode + ((getName() == null) ? 0 : getName().hashCode());
        hashCode = prime * hashCode + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
        hashCode = prime * hashCode + ((getOrganisms() == null) ? 0 : getOrganisms().hashCode());
        hashCode = prime * hashCode + ((getOwnerUsername() == null) ? 0 : getOwnerUsername().hashCode());
        hashCode = prime * hashCode + ((getTtl() == null) ? 0 : getTtl().hashCode());
        return hashCode;
    }

    @Override
    public TrainingRoomSession clone() {
        try {
            return (TrainingRoomSession) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TrainingRoomSessionMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
