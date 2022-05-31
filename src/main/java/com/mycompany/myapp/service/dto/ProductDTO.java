package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.GeneralStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String name;

    @Size(max = 200)
    private String description;

    /**
     * VendorCode - Article
     */
    @Schema(description = "VendorCode - Article")
    private String vendorCode;

    @NotNull
    @Size(max = 10)
    private String code;

    private Boolean top;

    /**
     * vatRate - ставка НДС
     */
    @Schema(description = "vatRate - ставка НДС")
    private Double vatRate;

    @Size(max = 30)
    private String madeIn;

    @Size(max = 200)
    private String info;

    private GeneralStatusEnum status;

    private BrandDTO brand;

    private AttachmentDTO photo;

    private CategoryDTO category;

    private GroupDTO group;

    private UnitDTO unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public GeneralStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GeneralStatusEnum status) {
        this.status = status;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public AttachmentDTO getPhoto() {
        return photo;
    }

    public void setPhoto(AttachmentDTO photo) {
        this.photo = photo;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
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
            ", brand=" + getBrand() +
            ", photo=" + getPhoto() +
            ", category=" + getCategory() +
            ", group=" + getGroup() +
            ", unit=" + getUnit() +
            "}";
    }
}
