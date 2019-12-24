/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/Stats" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class Stats implements Serializable, Cloneable, StructuredPojo {

    private Double genProgress;

    private Double orgsReportedSoFar;

    private Double orgsSpawnedSoFar;

    private Double totalOrgsPerGen;

    /**
     * @param genProgress
     */

    public void setGenProgress(Double genProgress) {
        this.genProgress = genProgress;
    }

    /**
     * @return
     */

    public Double getGenProgress() {
        return this.genProgress;
    }

    /**
     * @param genProgress
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Stats genProgress(Double genProgress) {
        setGenProgress(genProgress);
        return this;
    }

    /**
     * @param orgsReportedSoFar
     */

    public void setOrgsReportedSoFar(Double orgsReportedSoFar) {
        this.orgsReportedSoFar = orgsReportedSoFar;
    }

    /**
     * @return
     */

    public Double getOrgsReportedSoFar() {
        return this.orgsReportedSoFar;
    }

    /**
     * @param orgsReportedSoFar
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Stats orgsReportedSoFar(Double orgsReportedSoFar) {
        setOrgsReportedSoFar(orgsReportedSoFar);
        return this;
    }

    /**
     * @param orgsSpawnedSoFar
     */

    public void setOrgsSpawnedSoFar(Double orgsSpawnedSoFar) {
        this.orgsSpawnedSoFar = orgsSpawnedSoFar;
    }

    /**
     * @return
     */

    public Double getOrgsSpawnedSoFar() {
        return this.orgsSpawnedSoFar;
    }

    /**
     * @param orgsSpawnedSoFar
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Stats orgsSpawnedSoFar(Double orgsSpawnedSoFar) {
        setOrgsSpawnedSoFar(orgsSpawnedSoFar);
        return this;
    }

    /**
     * @param totalOrgsPerGen
     */

    public void setTotalOrgsPerGen(Double totalOrgsPerGen) {
        this.totalOrgsPerGen = totalOrgsPerGen;
    }

    /**
     * @return
     */

    public Double getTotalOrgsPerGen() {
        return this.totalOrgsPerGen;
    }

    /**
     * @param totalOrgsPerGen
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Stats totalOrgsPerGen(Double totalOrgsPerGen) {
        setTotalOrgsPerGen(totalOrgsPerGen);
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
        if (getGenProgress() != null)
            sb.append("GenProgress: ").append(getGenProgress()).append(",");
        if (getOrgsReportedSoFar() != null)
            sb.append("OrgsReportedSoFar: ").append(getOrgsReportedSoFar()).append(",");
        if (getOrgsSpawnedSoFar() != null)
            sb.append("OrgsSpawnedSoFar: ").append(getOrgsSpawnedSoFar()).append(",");
        if (getTotalOrgsPerGen() != null)
            sb.append("TotalOrgsPerGen: ").append(getTotalOrgsPerGen());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof Stats == false)
            return false;
        Stats other = (Stats) obj;
        if (other.getGenProgress() == null ^ this.getGenProgress() == null)
            return false;
        if (other.getGenProgress() != null && other.getGenProgress().equals(this.getGenProgress()) == false)
            return false;
        if (other.getOrgsReportedSoFar() == null ^ this.getOrgsReportedSoFar() == null)
            return false;
        if (other.getOrgsReportedSoFar() != null && other.getOrgsReportedSoFar().equals(this.getOrgsReportedSoFar()) == false)
            return false;
        if (other.getOrgsSpawnedSoFar() == null ^ this.getOrgsSpawnedSoFar() == null)
            return false;
        if (other.getOrgsSpawnedSoFar() != null && other.getOrgsSpawnedSoFar().equals(this.getOrgsSpawnedSoFar()) == false)
            return false;
        if (other.getTotalOrgsPerGen() == null ^ this.getTotalOrgsPerGen() == null)
            return false;
        if (other.getTotalOrgsPerGen() != null && other.getTotalOrgsPerGen().equals(this.getTotalOrgsPerGen()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getGenProgress() == null) ? 0 : getGenProgress().hashCode());
        hashCode = prime * hashCode + ((getOrgsReportedSoFar() == null) ? 0 : getOrgsReportedSoFar().hashCode());
        hashCode = prime * hashCode + ((getOrgsSpawnedSoFar() == null) ? 0 : getOrgsSpawnedSoFar().hashCode());
        hashCode = prime * hashCode + ((getTotalOrgsPerGen() == null) ? 0 : getTotalOrgsPerGen().hashCode());
        return hashCode;
    }

    @Override
    public Stats clone() {
        try {
            return (Stats) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.StatsMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
