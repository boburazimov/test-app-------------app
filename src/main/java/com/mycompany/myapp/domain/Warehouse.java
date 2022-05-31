package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Warehouse entity - Справочник складов\n@author Bobur Azimov.
 */
@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {

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

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "is_retail")
    private Boolean isRetail;

    @Column(name = "is_stock_control")
    private Boolean isStockControl;

    @Size(max = 200)
    @Column(name = "address", length = 200)
    private String address;

    @Size(max = 200)
    @Column(name = "info", length = 200)
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = { "group", "currency" }, allowSetters = true)
    private PriceType priceType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Group group;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Warehouse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Warehouse name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Warehouse code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsRetail() {
        return this.isRetail;
    }

    public Warehouse isRetail(Boolean isRetail) {
        this.setIsRetail(isRetail);
        return this;
    }

    public void setIsRetail(Boolean isRetail) {
        this.isRetail = isRetail;
    }

    public Boolean getIsStockControl() {
        return this.isStockControl;
    }

    public Warehouse isStockControl(Boolean isStockControl) {
        this.setIsStockControl(isStockControl);
        return this;
    }

    public void setIsStockControl(Boolean isStockControl) {
        this.isStockControl = isStockControl;
    }

    public String getAddress() {
        return this.address;
    }

    public Warehouse address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return this.info;
    }

    public Warehouse info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public PriceType getPriceType() {
        return this.priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public Warehouse priceType(PriceType priceType) {
        this.setPriceType(priceType);
        return this;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Warehouse group(Group group) {
        this.setGroup(group);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return id != null && id.equals(((Warehouse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", isRetail='" + getIsRetail() + "'" +
            ", isStockControl='" + getIsStockControl() + "'" +
            ", address='" + getAddress() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
