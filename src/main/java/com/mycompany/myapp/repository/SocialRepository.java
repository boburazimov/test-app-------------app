package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Social;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Social entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {}
