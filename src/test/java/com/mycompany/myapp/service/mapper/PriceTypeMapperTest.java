package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriceTypeMapperTest {

    private PriceTypeMapper priceTypeMapper;

    @BeforeEach
    public void setUp() {
        priceTypeMapper = new PriceTypeMapperImpl();
    }
}
