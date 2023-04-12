package zw.nmrl.onehealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Analysis.
 */
@Entity
@Table(name = "analysis")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Analysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "sample_id", nullable = false)
    private String sampleId;

    @NotNull
    @Column(name = "analysis_service_id", nullable = false)
    private String analysisServiceId;

    @Column(name = "result")
    private String result;

    @Column(name = "date_resulted")
    private LocalDate dateResulted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Analysis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleId() {
        return this.sampleId;
    }

    public Analysis sampleId(String sampleId) {
        this.setSampleId(sampleId);
        return this;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getAnalysisServiceId() {
        return this.analysisServiceId;
    }

    public Analysis analysisServiceId(String analysisServiceId) {
        this.setAnalysisServiceId(analysisServiceId);
        return this;
    }

    public void setAnalysisServiceId(String analysisServiceId) {
        this.analysisServiceId = analysisServiceId;
    }

    public String getResult() {
        return this.result;
    }

    public Analysis result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateResulted() {
        return this.dateResulted;
    }

    public Analysis dateResulted(LocalDate dateResulted) {
        this.setDateResulted(dateResulted);
        return this;
    }

    public void setDateResulted(LocalDate dateResulted) {
        this.dateResulted = dateResulted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Analysis)) {
            return false;
        }
        return id != null && id.equals(((Analysis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Analysis{" +
            "id=" + getId() +
            ", sampleId='" + getSampleId() + "'" +
            ", analysisServiceId='" + getAnalysisServiceId() + "'" +
            ", result='" + getResult() + "'" +
            ", dateResulted='" + getDateResulted() + "'" +
            "}";
    }
}
