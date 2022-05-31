package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.GeneralStatusEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

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

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    /**
     * VendorCode - Article
     */
    @Column(name = "vendor_code")
    private String vendorCode;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "top")
    private Boolean top;

    /**
     * vatRate - ставка НДС
     */
    @Column(name = "vat_rate")
    private Double vatRate;

    @Size(max = 30)
    @Column(name = "made_in", length = 30)
    private String madeIn;

    @Size(max = 200)
    @Column(name = "info", length = 200)
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatusEnum status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Brand brand;

    @ManyToOne
    private Attachment photo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Group group;

    @ManyToOne
    private Unit unit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public Product vendorCode(String vendorCode) {
        this.setVendorCode(vendorCode);
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getTop() {
        return this.top;
    }

    public Product top(Boolean top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Double getVatRate() {
        return this.vatRate;
    }

    public Product vatRate(Double vatRate) {
        this.setVatRate(vatRate);
        return this;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public String getMadeIn() {
        return this.madeIn;
    }

    public Product madeIn(String madeIn) {
        this.setMadeIn(madeIn);
        return this;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getInfo() {
        return this.info;
    }

    public Product info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public GeneralStatusEnum getStatus() {
        return this.status;
    }

    public Product status(GeneralStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(GeneralStatusEnum status) {
        this.status = status;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Attachment getPhoto() {
        return this.photo;
    }

    public void setPhoto(Attachment attachment) {
        this.photo = attachment;
    }

    public Product photo(Attachment attachment) {
        this.setPhoto(attachment);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Product group(Group group) {
        this.setGroup(group);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Product unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", code='" + getCode() + "'" +
            ", top='" + getTop() + "'" +
            ", vatRate=" + getVatRate() +
            ", madeIn='" + getMadeIn() + "'" +
            ", info='" + getInfo() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
