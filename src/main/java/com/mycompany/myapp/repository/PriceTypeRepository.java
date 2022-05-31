package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PriceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PriceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceTypeRepository extends JpaRepository<PriceType, Long> {}
