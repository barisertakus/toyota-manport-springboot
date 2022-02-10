package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.dto.IssueSearchDTO;
import com.barisertakus.toyotamanport.entity.Issue;
import com.barisertakus.toyotamanport.projection.IssueProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
    @Query("SELECT i.id as id, i.updatedDate as updatedDate, a.shortName as applicationName, " +
            "i.issueType as issueType, i.impactType as impactType, " +
            "i.description as description, p.country as plantCountry " +
            "FROM Issue i " +
            "LEFT JOIN i.applicationPlant ap " +
            "LEFT JOIN ap.application a " +
            "LEFT JOIN ap.plant p " +
            "WHERE i.isActive = TRUE " +
            "AND (:#{#search.applicationName} IS NULL OR a.shortName LIKE %:#{#search.applicationName}%) " +
            "AND (:#{#search.status} IS NULL OR i.status = :#{#search.status}) " +
            "AND (coalesce(:#{#search.fromDate}, '') = '' OR i.updatedDate >= :#{#search.fromDate}) " +
            "AND (coalesce(:#{#search.toDate}, '') = '' OR i.updatedDate <= :#{#search.toDate}) ")
    Page<IssueProjection> findAllFiltered(@Param("search") IssueSearchDTO search, Pageable pageable);
}
