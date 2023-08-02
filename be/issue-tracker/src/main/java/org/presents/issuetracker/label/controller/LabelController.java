package org.presents.issuetracker.label.controller;

import org.presents.issuetracker.label.dto.request.LabelRequestDto;
import org.presents.issuetracker.label.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody LabelRequestDto labelRequestDto) {
        labelService.create(labelRequestDto);
        return ResponseEntity.ok().build();
    }
}
