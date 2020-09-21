package com;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadRestClient {
	public static void main(String[] args) throws IOException {
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("file", getUserFileResource());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/LogAspect/api/upload/save", HttpMethod.POST,
				requestEntity, String.class);
		System.out.println("response status: " + response.getStatusCode());
		System.out.println("response body: " + response.getBody());
	}

	public static Resource getUserFileResource() throws IOException {
		// todo replace tempFile with a real file
		Path tempFile = Files.createTempFile("upload-test-file", ".txt");
		Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
		System.out.println("uploading: " + tempFile);
		File file = tempFile.toFile();
		// to upload in-memory bytes use ByteArrayResource instead
		return new FileSystemResource(file);
	}
}
