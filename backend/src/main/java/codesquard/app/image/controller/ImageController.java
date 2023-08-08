package codesquard.app.image.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.ResponseMessage;
import codesquard.app.image.service.ImageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api/images")
@RestController
public class ImageController {

	private final ImageService imageService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public ApiResponse<Object> uploadImage(@RequestPart(value = "image") MultipartFile multipartFile) throws
		IOException {
		return ApiResponse.of(HttpStatus.CREATED, ResponseMessage.IMAGE_SAVE_SUCCESS,
			Map.of("url", imageService.upload(multipartFile)));
	}

}
