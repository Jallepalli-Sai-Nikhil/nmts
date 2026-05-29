package com.nmts.search.controller;

import com.nmts.search.entity.MetalCatalog;
import com.nmts.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/metals/by-name")
    public ResponseEntity<List<MetalCatalog>> searchByMetalName(@RequestParam String metalName) {
        return ResponseEntity.ok(searchService.searchByName(metalName));
    }
}
