package codesquard.app.image;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageUploadController {

	private final S3Uploader s3Uploader;

	@PostMapping("/api/images")
	public ResponseEntity<String> uploadImage(@RequestPart(value = "image") MultipartFile multipartFile) throws
		IOException {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(s3Uploader.upload(multipartFile));
	}

}
