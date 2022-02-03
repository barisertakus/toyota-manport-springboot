package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Issue;
import com.barisertakus.toyotamanport.projection.IssueProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    Page<IssueProjection> findAllByIsActiveTrue(Pageable pageable);
}
