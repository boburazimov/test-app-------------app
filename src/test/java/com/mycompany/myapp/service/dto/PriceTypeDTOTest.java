package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriceTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceTypeDTO.class);
        PriceTypeDTO priceTypeDTO1 = new PriceTypeDTO();
        priceTypeDTO1.setId(1L);
        PriceTypeDTO priceTypeDTO2 = new PriceTypeDTO();
        assertThat(priceTypeDTO1).isNotEqualTo(priceTypeDTO2);
        priceTypeDTO2.setId(priceTypeDTO1.getId());
        assertThat(priceTypeDTO1).isEqualTo(priceTypeDTO2);
        priceTypeDTO2.setId(2L);
        assertThat(priceTypeDTO1).isNotEqualTo(priceTypeDTO2);
        priceTypeDTO1.setId(null);
        assertThat(priceTypeDTO1).isNotEqualTo(priceTypeDTO2);
    }
}
