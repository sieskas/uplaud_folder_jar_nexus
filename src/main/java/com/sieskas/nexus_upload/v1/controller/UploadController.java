package com.sieskas.nexus_upload.v1.controller;

import static com.sieskas.nexus_upload.shared.ApiPaths.API_BASE_PATH;
import static com.sieskas.nexus_upload.shared.ApiPaths.API_END_POINT_UPLOAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.sieskas.nexus_upload.service.UploadServiceImpl;
import com.sieskas.nexus_upload.v1.controller.resources.UploadRequestResource;
import com.sieskas.nexus_upload.v1.mapper.UploadMapper;
import java.lang.invoke.MethodHandles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_BASE_PATH)
@RequiredArgsConstructor
public class UploadController {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final UploadServiceImpl uploadServiceImpl;
	private final UploadMapper mapper;

	@PostMapping(
			value = API_END_POINT_UPLOAD,
			produces = {APPLICATION_JSON_VALUE},
			consumes = {APPLICATION_JSON_VALUE})
	public ResponseEntity<String> doUpload(
			@RequestHeader MultiValueMap<String, String> headers,
			@RequestBody UploadRequestResource requestResource) {

		return new ResponseEntity<>(
				uploadServiceImpl.upload(mapper.toModel(requestResource)), HttpStatus.OK);
	}
}
