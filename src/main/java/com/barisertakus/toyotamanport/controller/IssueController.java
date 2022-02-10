package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.IssueSearchDTO;
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

    @PostMapping("/getAll")
    public ResponseEntity<Page<IssueProjection>> findAllFiltered(@RequestBody IssueSearchDTO issueSearchDTO, @RequestParam int pageNo, int pageSize, String sortType, String sortField){
        return ResponseEntity.ok(issueService.findAllFiltered(issueSearchDTO, pageNo, pageSize, sortType, sortField));
    }

}
