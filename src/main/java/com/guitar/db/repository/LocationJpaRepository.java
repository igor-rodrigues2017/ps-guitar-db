package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {
    List<Location> findLocationByStateLike(String stateName);

    List<Location> findLocationByStateLikeOrCountryLike(String stateName, String countryName);

    List<Location> findLocationByCountry(String countryName);

    List<Location> findLocationByStateAndCountry(String stateName, String countryName);
}
