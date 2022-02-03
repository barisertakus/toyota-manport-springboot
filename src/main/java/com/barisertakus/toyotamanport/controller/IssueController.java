package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.projection.IssueProjection;
import com.barisertakus.toyotamanport.service.IssueService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/issues")
@CrossOrigin
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<IssueProjection>> findAllByIsActiveTrue(@RequestParam int pageNo, int pageSize, String sortType, String sortField){
        return ResponseEntity.ok(issueService.findAllByIsActiveTrue(pageNo, pageSize, sortType, sortField));
    }

}
