package com.sieskas.nexus_upload.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest {

	public String username;
	public String password;
	public String url;
	public String path;
	public List<FileLibrary> files;
	public String groupId;
	public String prefix;
	public String artifactId;
	public String version;
}
