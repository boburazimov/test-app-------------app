package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Barcode} entity.
 */
public class BarcodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BarcodeDTO)) {
            return false;
        }

        BarcodeDTO barcodeDTO = (BarcodeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, barcodeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BarcodeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}
