package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BarcodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barcode.class);
        Barcode barcode1 = new Barcode();
        barcode1.setId(1L);
        Barcode barcode2 = new Barcode();
        barcode2.setId(barcode1.getId());
        assertThat(barcode1).isEqualTo(barcode2);
        barcode2.setId(2L);
        assertThat(barcode1).isNotEqualTo(barcode2);
        barcode1.setId(null);
        assertThat(barcode1).isNotEqualTo(barcode2);
    }
}
