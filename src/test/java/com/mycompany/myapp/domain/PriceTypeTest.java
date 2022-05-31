package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriceTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceType.class);
        PriceType priceType1 = new PriceType();
        priceType1.setId(1L);
        PriceType priceType2 = new PriceType();
        priceType2.setId(priceType1.getId());
        assertThat(priceType1).isEqualTo(priceType2);
        priceType2.setId(2L);
        assertThat(priceType1).isNotEqualTo(priceType2);
        priceType1.setId(null);
        assertThat(priceType1).isNotEqualTo(priceType2);
    }
}
