package org.presents.issuetracker.label.controller;

import lombok.extern.slf4j.Slf4j;
import org.presents.issuetracker.global.dto.response.LabelResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.dto.response.LabelDetailResponse;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public ResponseEntity<List<LabelDetailResponse>> getLabelDetails() {
        List<LabelDetailResponse> labelDetails = labelService.getLabelDetails();
        return ResponseEntity.ok().body(labelDetails);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<LabelPreviewResponse>> getLabelPreviews() {
        List<LabelPreviewResponse> labelPreviews = labelService.getLabelPreviews();
        return ResponseEntity.ok().body(labelPreviews);
    }

    @PostMapping
    public ResponseEntity<LabelResponse> create(@RequestBody LabelCreateRequest labelCreateRequest) {
        LabelResponse labelResponse = labelService.create(labelCreateRequest);
        return ResponseEntity.ok().body(labelResponse);
    }

    @PatchMapping
    public ResponseEntity<LabelResponse> update(@RequestBody LabelUpdateRequest dto) {
        LabelResponse labelResponse = labelService.update(dto);
        return ResponseEntity.ok().body(labelResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
