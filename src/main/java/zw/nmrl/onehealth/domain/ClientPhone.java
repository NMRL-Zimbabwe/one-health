package zw.nmrl.onehealth.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ClientPhone.
 */
@Entity
@Table(name = "client_phone")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "client_id")
    private String clientId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientPhone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public ClientPhone number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClientId() {
        return this.clientId;
    }

    public ClientPhone clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientPhone)) {
            return false;
        }
        return id != null && id.equals(((ClientPhone) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientPhone{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", clientId='" + getClientId() + "'" +
            "}";
    }
}
