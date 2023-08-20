package codesquad.kr.gyeonggidoidle.issuetracker.domain.file.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        return fileService.uploadFileV1(multipartFile);
    }
}
