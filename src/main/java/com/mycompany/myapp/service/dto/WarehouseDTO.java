package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Warehouse} entity.
 */
@Schema(description = "Warehouse entity - Справочник складов\n@author Bobur Azimov.")
public class WarehouseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    @Size(max = 10)
    private String code;

    private Boolean isRetail;

    private Boolean isStockControl;

    @Size(max = 200)
    private String address;

    @Size(max = 200)
    private String info;

    private PriceTypeDTO priceType;

    private GroupDTO group;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsRetail() {
        return isRetail;
    }

    public void setIsRetail(Boolean isRetail) {
        this.isRetail = isRetail;
    }

    public Boolean getIsStockControl() {
        return isStockControl;
    }

    public void setIsStockControl(Boolean isStockControl) {
        this.isStockControl = isStockControl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public PriceTypeDTO getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceTypeDTO priceType) {
        this.priceType = priceType;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarehouseDTO)) {
            return false;
        }

        WarehouseDTO warehouseDTO = (WarehouseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warehouseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", isRetail='" + getIsRetail() + "'" +
            ", isStockControl='" + getIsStockControl() + "'" +
            ", address='" + getAddress() + "'" +
            ", info='" + getInfo() + "'" +
            ", priceType=" + getPriceType() +
            ", group=" + getGroup() +
            "}";
    }
}
