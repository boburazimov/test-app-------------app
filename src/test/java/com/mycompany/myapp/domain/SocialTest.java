package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Social.class);
        Social social1 = new Social();
        social1.setId(1L);
        Social social2 = new Social();
        social2.setId(social1.getId());
        assertThat(social1).isEqualTo(social2);
        social2.setId(2L);
        assertThat(social1).isNotEqualTo(social2);
        social1.setId(null);
        assertThat(social1).isNotEqualTo(social2);
    }
}
