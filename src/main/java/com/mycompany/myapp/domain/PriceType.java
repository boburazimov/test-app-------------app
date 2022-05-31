package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.GeneralStatusEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * PriceType entity - Виды прайсов оптовая или рознич.\n@author Bobur Azimov.
 */
@Entity
@Table(name = "price_type")
public class PriceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "name", length = 40, nullable = false, unique = true)
    private String name;

    @Column(name = "include_vat")
    private Boolean includeVat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatusEnum status;

    @Size(max = 200)
    @Column(name = "info", length = 200)
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Group group;

    @ManyToOne
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PriceType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PriceType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIncludeVat() {
        return this.includeVat;
    }

    public PriceType includeVat(Boolean includeVat) {
        this.setIncludeVat(includeVat);
        return this;
    }

    public void setIncludeVat(Boolean includeVat) {
        this.includeVat = includeVat;
    }

    public GeneralStatusEnum getStatus() {
        return this.status;
    }

    public PriceType status(GeneralStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(GeneralStatusEnum status) {
        this.status = status;
    }

    public String getInfo() {
        return this.info;
    }

    public PriceType info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public PriceType group(Group group) {
        this.setGroup(group);
        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PriceType currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceType)) {
            return false;
        }
        return id != null && id.equals(((PriceType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriceType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", includeVat='" + getIncludeVat() + "'" +
            ", status='" + getStatus() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
