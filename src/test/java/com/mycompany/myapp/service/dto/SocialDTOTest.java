package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialDTO.class);
        SocialDTO socialDTO1 = new SocialDTO();
        socialDTO1.setId(1L);
        SocialDTO socialDTO2 = new SocialDTO();
        assertThat(socialDTO1).isNotEqualTo(socialDTO2);
        socialDTO2.setId(socialDTO1.getId());
        assertThat(socialDTO1).isEqualTo(socialDTO2);
        socialDTO2.setId(2L);
        assertThat(socialDTO1).isNotEqualTo(socialDTO2);
        socialDTO1.setId(null);
        assertThat(socialDTO1).isNotEqualTo(socialDTO2);
    }
}
