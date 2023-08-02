package org.presents.issuetracker.label.controller;

import lombok.extern.slf4j.Slf4j;
import org.presents.issuetracker.label.dto.request.LabelRequestDto;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequestDto;
import org.presents.issuetracker.label.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @PatchMapping
    public ResponseEntity<Long> update(@RequestBody LabelUpdateRequestDto dto) {
        Long id = labelService.update(dto);
        return ResponseEntity.ok().body(id);
    }

}
