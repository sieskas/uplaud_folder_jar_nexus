package com.sieskas.nexus_upload.outcall.nexus;

import com.sieskas.nexus_upload.exception.UploadException;
import com.sieskas.nexus_upload.model.UploadRequest;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class NexusImpl implements NexusPort {

	private static final Logger logger = LogManager.getLogger(NexusImpl.class);

	@Override
	public void upload(UploadRequest uploadRequest) {
		String authHeader =
				"Basic "
						+ Base64.getEncoder()
								.encodeToString(
										(uploadRequest.getUsername() + ":" + uploadRequest.getPassword()).getBytes());

		WebClient client =
				WebClient.builder().defaultHeader(HttpHeaders.AUTHORIZATION, authHeader).build();

		uploadRequest
				.getFiles()
				.forEach(
						file -> {
							MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

							formData.add("maven2.generate-pom", "true");
							formData.add("maven2.groupId", uploadRequest.getGroupId());
							formData.add("maven2.packaging", "jar");
							formData.add("version", file.getVersion());

							formData.add("maven2.artifactId", file.getName());
							formData.add("maven2.asset1", new FileSystemResource(file.getPath()));
							formData.add("maven2.asset1.extension", file.getExtension());

							client
									.post()
									.uri(uploadRequest.getUrl())
									.contentType(MediaType.MULTIPART_FORM_DATA)
									.bodyValue(formData)
									.exchange()
									.flatMap(
											response -> {
												if (response.statusCode().is2xxSuccessful()) {
													return Mono.just(response);
												} else {
													return response
															.bodyToMono(String.class)
															.flatMap(
																	body ->
																			Mono.error(
																					new UploadException("Error uploading file : " + body)));
												}
											})
									.subscribe(
											response -> {
												logger.info("Upload success : {}", file.getName());
											},
											error -> {
												logger.error("Error during file upload : {}", error.getMessage());
											});
						});
	}
}
