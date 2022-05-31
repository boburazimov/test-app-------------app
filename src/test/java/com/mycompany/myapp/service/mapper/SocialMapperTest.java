package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocialMapperTest {

    private SocialMapper socialMapper;

    @BeforeEach
    public void setUp() {
        socialMapper = new SocialMapperImpl();
    }
}
