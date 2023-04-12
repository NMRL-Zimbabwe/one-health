package zw.nmrl.onehealth.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Sample.
 */
@Entity
@Table(name = "sample")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "sample_type_id", nullable = false)
    private String sampleTypeId;

    @NotNull
    @Column(name = "analysis_service_id", nullable = false)
    private String analysisServiceId;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sample id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleTypeId() {
        return this.sampleTypeId;
    }

    public Sample sampleTypeId(String sampleTypeId) {
        this.setSampleTypeId(sampleTypeId);
        return this;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getAnalysisServiceId() {
        return this.analysisServiceId;
    }

    public Sample analysisServiceId(String analysisServiceId) {
        this.setAnalysisServiceId(analysisServiceId);
        return this;
    }

    public void setAnalysisServiceId(String analysisServiceId) {
        this.analysisServiceId = analysisServiceId;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Sample longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Sample latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sample)) {
            return false;
        }
        return id != null && id.equals(((Sample) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sample{" +
            "id=" + getId() +
            ", sampleTypeId='" + getSampleTypeId() + "'" +
            ", analysisServiceId='" + getAnalysisServiceId() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            "}";
    }
}
