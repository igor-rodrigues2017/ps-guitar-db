package com.guitar.db.repository;

import com.guitar.db.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactureJpaRepository extends JpaRepository<Manufacturer, Long> {
}
