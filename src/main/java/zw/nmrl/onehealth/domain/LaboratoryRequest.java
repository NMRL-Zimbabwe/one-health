package zw.nmrl.onehealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LaboratoryRequest.
 */
@Entity
@Table(name = "laboratory_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LaboratoryRequest implements Serializable {

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
    @Column(name = "sample_type_id")
    private String sampleTypeId;

    @Column(name = "date_collected")
    private LocalDate dateCollected;

    @Column(name = "date_received")
    private LocalDate dateReceived;

    @Column(name = "sample_condition")
    private String sampleCondition;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "status")
    private String status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "sector_id")
    private String sectorId;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "procince_id")
    private String procinceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LaboratoryRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleId() {
        return this.sampleId;
    }

    public LaboratoryRequest sampleId(String sampleId) {
        this.setSampleId(sampleId);
        return this;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleTypeId() {
        return this.sampleTypeId;
    }

    public LaboratoryRequest sampleTypeId(String sampleTypeId) {
        this.setSampleTypeId(sampleTypeId);
        return this;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public LocalDate getDateCollected() {
        return this.dateCollected;
    }

    public LaboratoryRequest dateCollected(LocalDate dateCollected) {
        this.setDateCollected(dateCollected);
        return this;
    }

    public void setDateCollected(LocalDate dateCollected) {
        this.dateCollected = dateCollected;
    }

    public LocalDate getDateReceived() {
        return this.dateReceived;
    }

    public LaboratoryRequest dateReceived(LocalDate dateReceived) {
        this.setDateReceived(dateReceived);
        return this;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getSampleCondition() {
        return this.sampleCondition;
    }

    public LaboratoryRequest sampleCondition(String sampleCondition) {
        this.setSampleCondition(sampleCondition);
        return this;
    }

    public void setSampleCondition(String sampleCondition) {
        this.sampleCondition = sampleCondition;
    }

    public String getClientId() {
        return this.clientId;
    }

    public LaboratoryRequest clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LaboratoryRequest priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return this.status;
    }

    public LaboratoryRequest status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public LaboratoryRequest remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public LaboratoryRequest locationId(String locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSectorId() {
        return this.sectorId;
    }

    public LaboratoryRequest sectorId(String sectorId) {
        this.setSectorId(sectorId);
        return this;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getDistrictId() {
        return this.districtId;
    }

    public LaboratoryRequest districtId(String districtId) {
        this.setDistrictId(districtId);
        return this;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getProcinceId() {
        return this.procinceId;
    }

    public LaboratoryRequest procinceId(String procinceId) {
        this.setProcinceId(procinceId);
        return this;
    }

    public void setProcinceId(String procinceId) {
        this.procinceId = procinceId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratoryRequest)) {
            return false;
        }
        return id != null && id.equals(((LaboratoryRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LaboratoryRequest{" +
            "id=" + getId() +
            ", sampleId='" + getSampleId() + "'" +
            ", sampleTypeId='" + getSampleTypeId() + "'" +
            ", dateCollected='" + getDateCollected() + "'" +
            ", dateReceived='" + getDateReceived() + "'" +
            ", sampleCondition='" + getSampleCondition() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", priority=" + getPriority() +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", sectorId='" + getSectorId() + "'" +
            ", districtId='" + getDistrictId() + "'" +
            ", procinceId='" + getProcinceId() + "'" +
            "}";
    }
}
