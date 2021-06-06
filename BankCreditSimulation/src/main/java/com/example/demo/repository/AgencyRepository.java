package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Agency;
import org.springframework.data.repository.query.Param;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long>{
	
	@Query("SELECT DISTINCT  agency from Agency agency where agency.city=:city ")
	public List<Agency> getallAgencybyCity(@Param("city") String city);
	
	@Query( "SELECT count (*) from Agency agency where agency.city=:city")
	public long getNombreAgencyJPQL(@Param("city") String city);
	
	@Query("SELECT agency from Agency agency where "
			+"CONCAT(agency.id, agency.agencyName, agency.city, agency.adress, agency.contact, agency.email)"
			+ "LIKE %?1%")
	public List<Agency> searchAgencybyName(String keyword);


}
