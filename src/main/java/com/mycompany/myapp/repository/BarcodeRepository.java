package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Barcode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Barcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long> {}
