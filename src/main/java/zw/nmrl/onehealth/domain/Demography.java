package zw.nmrl.onehealth.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Demography.
 */
@Entity
@Table(name = "demography")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Demography implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "record_id", nullable = false)
    private String recordId;

    @NotNull
    @Column(name = "demographic_coding_id", nullable = false)
    private String demographicCodingId;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demography id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public Demography recordId(String recordId) {
        this.setRecordId(recordId);
        return this;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDemographicCodingId() {
        return this.demographicCodingId;
    }

    public Demography demographicCodingId(String demographicCodingId) {
        this.setDemographicCodingId(demographicCodingId);
        return this;
    }

    public void setDemographicCodingId(String demographicCodingId) {
        this.demographicCodingId = demographicCodingId;
    }

    public String getValue() {
        return this.value;
    }

    public Demography value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demography)) {
            return false;
        }
        return id != null && id.equals(((Demography) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demography{" +
            "id=" + getId() +
            ", recordId='" + getRecordId() + "'" +
            ", demographicCodingId='" + getDemographicCodingId() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
