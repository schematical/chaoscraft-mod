/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TaxonomicRank" target="_top">AWS
 *      API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TaxonomicRank implements Serializable, Cloneable, StructuredPojo {

    private Double age;

    private Double childrenReportedThisGen;

    private Double childrenSpawnedThisGen;

    private Double currScore;

    private Double generation;

    private Double gensSinceLastImprovement;

    private Double highScore;

    private java.util.List<HistoricalScoresElement> historicalScores;

    private String historicalScoresRaw;

    private String name;

    private String namespace;

    private String observedAttributesRaw;

    private String ownerUsername;

    private String parentNamespace;

    private String trainingRoomNamespace;

    private String trankClass;

    /**
     * @param age
     */

    public void setAge(Double age) {
        this.age = age;
    }

    /**
     * @return
     */

    public Double getAge() {
        return this.age;
    }

    /**
     * @param age
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank age(Double age) {
        setAge(age);
        return this;
    }

    /**
     * @param childrenReportedThisGen
     */

    public void setChildrenReportedThisGen(Double childrenReportedThisGen) {
        this.childrenReportedThisGen = childrenReportedThisGen;
    }

    /**
     * @return
     */

    public Double getChildrenReportedThisGen() {
        return this.childrenReportedThisGen;
    }

    /**
     * @param childrenReportedThisGen
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank childrenReportedThisGen(Double childrenReportedThisGen) {
        setChildrenReportedThisGen(childrenReportedThisGen);
        return this;
    }

    /**
     * @param childrenSpawnedThisGen
     */

    public void setChildrenSpawnedThisGen(Double childrenSpawnedThisGen) {
        this.childrenSpawnedThisGen = childrenSpawnedThisGen;
    }

    /**
     * @return
     */

    public Double getChildrenSpawnedThisGen() {
        return this.childrenSpawnedThisGen;
    }

    /**
     * @param childrenSpawnedThisGen
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank childrenSpawnedThisGen(Double childrenSpawnedThisGen) {
        setChildrenSpawnedThisGen(childrenSpawnedThisGen);
        return this;
    }

    /**
     * @param currScore
     */

    public void setCurrScore(Double currScore) {
        this.currScore = currScore;
    }

    /**
     * @return
     */

    public Double getCurrScore() {
        return this.currScore;
    }

    /**
     * @param currScore
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank currScore(Double currScore) {
        setCurrScore(currScore);
        return this;
    }

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

    public TaxonomicRank generation(Double generation) {
        setGeneration(generation);
        return this;
    }

    /**
     * @param gensSinceLastImprovement
     */

    public void setGensSinceLastImprovement(Double gensSinceLastImprovement) {
        this.gensSinceLastImprovement = gensSinceLastImprovement;
    }

    /**
     * @return
     */

    public Double getGensSinceLastImprovement() {
        return this.gensSinceLastImprovement;
    }

    /**
     * @param gensSinceLastImprovement
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank gensSinceLastImprovement(Double gensSinceLastImprovement) {
        setGensSinceLastImprovement(gensSinceLastImprovement);
        return this;
    }

    /**
     * @param highScore
     */

    public void setHighScore(Double highScore) {
        this.highScore = highScore;
    }

    /**
     * @return
     */

    public Double getHighScore() {
        return this.highScore;
    }

    /**
     * @param highScore
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank highScore(Double highScore) {
        setHighScore(highScore);
        return this;
    }

    /**
     * @return
     */

    public java.util.List<HistoricalScoresElement> getHistoricalScores() {
        return historicalScores;
    }

    /**
     * @param historicalScores
     */

    public void setHistoricalScores(java.util.Collection<HistoricalScoresElement> historicalScores) {
        if (historicalScores == null) {
            this.historicalScores = null;
            return;
        }

        this.historicalScores = new java.util.ArrayList<HistoricalScoresElement>(historicalScores);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setHistoricalScores(java.util.Collection)} or {@link #withHistoricalScores(java.util.Collection)} if you
     * want to override the existing values.
     * </p>
     * 
     * @param historicalScores
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank historicalScores(HistoricalScoresElement... historicalScores) {
        if (this.historicalScores == null) {
            setHistoricalScores(new java.util.ArrayList<HistoricalScoresElement>(historicalScores.length));
        }
        for (HistoricalScoresElement ele : historicalScores) {
            this.historicalScores.add(ele);
        }
        return this;
    }

    /**
     * @param historicalScores
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank historicalScores(java.util.Collection<HistoricalScoresElement> historicalScores) {
        setHistoricalScores(historicalScores);
        return this;
    }

    /**
     * @param historicalScoresRaw
     */

    public void setHistoricalScoresRaw(String historicalScoresRaw) {
        this.historicalScoresRaw = historicalScoresRaw;
    }

    /**
     * @return
     */

    public String getHistoricalScoresRaw() {
        return this.historicalScoresRaw;
    }

    /**
     * @param historicalScoresRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank historicalScoresRaw(String historicalScoresRaw) {
        setHistoricalScoresRaw(historicalScoresRaw);
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

    public TaxonomicRank name(String name) {
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

    public TaxonomicRank namespace(String namespace) {
        setNamespace(namespace);
        return this;
    }

    /**
     * @param observedAttributesRaw
     */

    public void setObservedAttributesRaw(String observedAttributesRaw) {
        this.observedAttributesRaw = observedAttributesRaw;
    }

    /**
     * @return
     */

    public String getObservedAttributesRaw() {
        return this.observedAttributesRaw;
    }

    /**
     * @param observedAttributesRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank observedAttributesRaw(String observedAttributesRaw) {
        setObservedAttributesRaw(observedAttributesRaw);
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

    public TaxonomicRank ownerUsername(String ownerUsername) {
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

    public TaxonomicRank parentNamespace(String parentNamespace) {
        setParentNamespace(parentNamespace);
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

    public TaxonomicRank trainingRoomNamespace(String trainingRoomNamespace) {
        setTrainingRoomNamespace(trainingRoomNamespace);
        return this;
    }

    /**
     * @param trankClass
     */

    public void setTrankClass(String trankClass) {
        this.trankClass = trankClass;
    }

    /**
     * @return
     */

    public String getTrankClass() {
        return this.trankClass;
    }

    /**
     * @param trankClass
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TaxonomicRank trankClass(String trankClass) {
        setTrankClass(trankClass);
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
        if (getAge() != null)
            sb.append("Age: ").append(getAge()).append(",");
        if (getChildrenReportedThisGen() != null)
            sb.append("ChildrenReportedThisGen: ").append(getChildrenReportedThisGen()).append(",");
        if (getChildrenSpawnedThisGen() != null)
            sb.append("ChildrenSpawnedThisGen: ").append(getChildrenSpawnedThisGen()).append(",");
        if (getCurrScore() != null)
            sb.append("CurrScore: ").append(getCurrScore()).append(",");
        if (getGeneration() != null)
            sb.append("Generation: ").append(getGeneration()).append(",");
        if (getGensSinceLastImprovement() != null)
            sb.append("GensSinceLastImprovement: ").append(getGensSinceLastImprovement()).append(",");
        if (getHighScore() != null)
            sb.append("HighScore: ").append(getHighScore()).append(",");
        if (getHistoricalScores() != null)
            sb.append("HistoricalScores: ").append(getHistoricalScores()).append(",");
        if (getHistoricalScoresRaw() != null)
            sb.append("HistoricalScoresRaw: ").append(getHistoricalScoresRaw()).append(",");
        if (getName() != null)
            sb.append("Name: ").append(getName()).append(",");
        if (getNamespace() != null)
            sb.append("Namespace: ").append(getNamespace()).append(",");
        if (getObservedAttributesRaw() != null)
            sb.append("ObservedAttributesRaw: ").append(getObservedAttributesRaw()).append(",");
        if (getOwnerUsername() != null)
            sb.append("OwnerUsername: ").append(getOwnerUsername()).append(",");
        if (getParentNamespace() != null)
            sb.append("ParentNamespace: ").append(getParentNamespace()).append(",");
        if (getTrainingRoomNamespace() != null)
            sb.append("TrainingRoomNamespace: ").append(getTrainingRoomNamespace()).append(",");
        if (getTrankClass() != null)
            sb.append("TrankClass: ").append(getTrankClass());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TaxonomicRank == false)
            return false;
        TaxonomicRank other = (TaxonomicRank) obj;
        if (other.getAge() == null ^ this.getAge() == null)
            return false;
        if (other.getAge() != null && other.getAge().equals(this.getAge()) == false)
            return false;
        if (other.getChildrenReportedThisGen() == null ^ this.getChildrenReportedThisGen() == null)
            return false;
        if (other.getChildrenReportedThisGen() != null && other.getChildrenReportedThisGen().equals(this.getChildrenReportedThisGen()) == false)
            return false;
        if (other.getChildrenSpawnedThisGen() == null ^ this.getChildrenSpawnedThisGen() == null)
            return false;
        if (other.getChildrenSpawnedThisGen() != null && other.getChildrenSpawnedThisGen().equals(this.getChildrenSpawnedThisGen()) == false)
            return false;
        if (other.getCurrScore() == null ^ this.getCurrScore() == null)
            return false;
        if (other.getCurrScore() != null && other.getCurrScore().equals(this.getCurrScore()) == false)
            return false;
        if (other.getGeneration() == null ^ this.getGeneration() == null)
            return false;
        if (other.getGeneration() != null && other.getGeneration().equals(this.getGeneration()) == false)
            return false;
        if (other.getGensSinceLastImprovement() == null ^ this.getGensSinceLastImprovement() == null)
            return false;
        if (other.getGensSinceLastImprovement() != null && other.getGensSinceLastImprovement().equals(this.getGensSinceLastImprovement()) == false)
            return false;
        if (other.getHighScore() == null ^ this.getHighScore() == null)
            return false;
        if (other.getHighScore() != null && other.getHighScore().equals(this.getHighScore()) == false)
            return false;
        if (other.getHistoricalScores() == null ^ this.getHistoricalScores() == null)
            return false;
        if (other.getHistoricalScores() != null && other.getHistoricalScores().equals(this.getHistoricalScores()) == false)
            return false;
        if (other.getHistoricalScoresRaw() == null ^ this.getHistoricalScoresRaw() == null)
            return false;
        if (other.getHistoricalScoresRaw() != null && other.getHistoricalScoresRaw().equals(this.getHistoricalScoresRaw()) == false)
            return false;
        if (other.getName() == null ^ this.getName() == null)
            return false;
        if (other.getName() != null && other.getName().equals(this.getName()) == false)
            return false;
        if (other.getNamespace() == null ^ this.getNamespace() == null)
            return false;
        if (other.getNamespace() != null && other.getNamespace().equals(this.getNamespace()) == false)
            return false;
        if (other.getObservedAttributesRaw() == null ^ this.getObservedAttributesRaw() == null)
            return false;
        if (other.getObservedAttributesRaw() != null && other.getObservedAttributesRaw().equals(this.getObservedAttributesRaw()) == false)
            return false;
        if (other.getOwnerUsername() == null ^ this.getOwnerUsername() == null)
            return false;
        if (other.getOwnerUsername() != null && other.getOwnerUsername().equals(this.getOwnerUsername()) == false)
            return false;
        if (other.getParentNamespace() == null ^ this.getParentNamespace() == null)
            return false;
        if (other.getParentNamespace() != null && other.getParentNamespace().equals(this.getParentNamespace()) == false)
            return false;
        if (other.getTrainingRoomNamespace() == null ^ this.getTrainingRoomNamespace() == null)
            return false;
        if (other.getTrainingRoomNamespace() != null && other.getTrainingRoomNamespace().equals(this.getTrainingRoomNamespace()) == false)
            return false;
        if (other.getTrankClass() == null ^ this.getTrankClass() == null)
            return false;
        if (other.getTrankClass() != null && other.getTrankClass().equals(this.getTrankClass()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAge() == null) ? 0 : getAge().hashCode());
        hashCode = prime * hashCode + ((getChildrenReportedThisGen() == null) ? 0 : getChildrenReportedThisGen().hashCode());
        hashCode = prime * hashCode + ((getChildrenSpawnedThisGen() == null) ? 0 : getChildrenSpawnedThisGen().hashCode());
        hashCode = prime * hashCode + ((getCurrScore() == null) ? 0 : getCurrScore().hashCode());
        hashCode = prime * hashCode + ((getGeneration() == null) ? 0 : getGeneration().hashCode());
        hashCode = prime * hashCode + ((getGensSinceLastImprovement() == null) ? 0 : getGensSinceLastImprovement().hashCode());
        hashCode = prime * hashCode + ((getHighScore() == null) ? 0 : getHighScore().hashCode());
        hashCode = prime * hashCode + ((getHistoricalScores() == null) ? 0 : getHistoricalScores().hashCode());
        hashCode = prime * hashCode + ((getHistoricalScoresRaw() == null) ? 0 : getHistoricalScoresRaw().hashCode());
        hashCode = prime * hashCode + ((getName() == null) ? 0 : getName().hashCode());
        hashCode = prime * hashCode + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
        hashCode = prime * hashCode + ((getObservedAttributesRaw() == null) ? 0 : getObservedAttributesRaw().hashCode());
        hashCode = prime * hashCode + ((getOwnerUsername() == null) ? 0 : getOwnerUsername().hashCode());
        hashCode = prime * hashCode + ((getParentNamespace() == null) ? 0 : getParentNamespace().hashCode());
        hashCode = prime * hashCode + ((getTrainingRoomNamespace() == null) ? 0 : getTrainingRoomNamespace().hashCode());
        hashCode = prime * hashCode + ((getTrankClass() == null) ? 0 : getTrankClass().hashCode());
        return hashCode;
    }

    @Override
    public TaxonomicRank clone() {
        try {
            return (TaxonomicRank) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TaxonomicRankMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
