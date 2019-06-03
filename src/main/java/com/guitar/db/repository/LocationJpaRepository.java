package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {
    List<Location> findByStateLike(String stateName);

    List<Location> findByStateNotLikeOrderByStateDesc(String notContain);

    List<Location> findByStateLikeOrStateLike(String stateName1, String stateName2);

    List<Location> findByCountry(String countryName);

    List<Location> findByStateAndCountry(String stateName, String countryName);

    List<Location> findByStateStartingWithIgnoreCase(String startWith); //AI%

    List<Location> findByStateContainingIgnoreCase(String contains); //%in%

    List<Location> findByStateEndingWithIgnoreCase(String endWith); //%ia

    Location findFirstByStateStartingWithIgnoreCase(String startWith);

}
