package com.guitar.db;

import com.guitar.db.model.Location;
import com.guitar.db.repository.LocationJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationPersistenceTests {
	@Autowired
	private LocationJpaRepository locationJpaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void jpaTestFind(){
		List<Location> locations = locationJpaRepository.findAll();
		assertNotNull(locations);
	}

	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
		location = locationJpaRepository.saveAndFlush(location);
		
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Location otherLocation = locationJpaRepository.findOne(location.getId());
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());
		
		//delete BC location now
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());
	}

	@Test
	public void testFindStateLikeOrStateLike(){
		List<Location> locations = locationJpaRepository.findByStateLikeOrStateLike("Id%", "New%");
		assertTrue(locations.size() > 0);
	}

	@Test
	public void testFindByCountry(){
		List<Location> loc = locationJpaRepository.findByCountry("United States");
		assertEquals("United States", loc.get(0).getCountry());
	}

	@Test
	public void testFindByStateAndCountry(){
		List<Location> loc = locationJpaRepository.findByStateAndCountry("New York", "United States");
		assertEquals("New York", loc.get(0).getState());
	}

	@Test
	public void testFindStartEndAndContainsKeyWords(){
		List<Location> locs = locationJpaRepository.findByStateContainingIgnoreCase("york");
		assertEquals("New York", locs.get(0).getState());

		locs = locationJpaRepository.findByStateStartingWithIgnoreCase("ariz");
		assertEquals("Arizona", locs.get(0).getState());

		locs = locationJpaRepository.findByStateEndingWithIgnoreCase("orida");
		assertEquals("Florida", locs.get(0).getState());
	}

	@Test
	@Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
	public void testFindWithChildren() throws Exception {
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());
		
		assertEquals(1, arizona.getManufacturers().size());
		
		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}

	@Test
	public void testNotContainInOrder(){
		List<Location> locs = locationJpaRepository.findByStateNotLikeOrderByStateDesc("New%");
		locs.forEach(location -> System.out.println(location.getState()));
		assertEquals("W", locs.get(0).getState().substring(0, 1));

	}

	@Test
	public void testFindFirst(){
		Location first = locationJpaRepository.findFirstByStateStartingWithIgnoreCase("New");
		assertEquals("New Hampshire", first.getState());
	}
}
