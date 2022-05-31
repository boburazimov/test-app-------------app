package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.GeneralStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PriceType} entity.
 */
@Schema(description = "PriceType entity - Виды прайсов оптовая или рознич.\n@author Bobur Azimov.")
public class PriceTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String name;

    private Boolean includeVat;

    private GeneralStatusEnum status;

    @Size(max = 200)
    private String info;

    private GroupDTO group;

    private CurrencyDTO currency;

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

    public Boolean getIncludeVat() {
        return includeVat;
    }

    public void setIncludeVat(Boolean includeVat) {
        this.includeVat = includeVat;
    }

    public GeneralStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GeneralStatusEnum status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceTypeDTO)) {
            return false;
        }

        PriceTypeDTO priceTypeDTO = (PriceTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, priceTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriceTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", includeVat='" + getIncludeVat() + "'" +
            ", status='" + getStatus() + "'" +
            ", info='" + getInfo() + "'" +
            ", group=" + getGroup() +
            ", currency=" + getCurrency() +
            "}";
    }
}
