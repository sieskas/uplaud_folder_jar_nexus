package com.sieskas.nexus_upload.v1.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequestResource {

	@JsonProperty("username")
	public String username;

	@JsonProperty("password")
	public String password;

	@JsonProperty("url")
	public String url;

	@JsonProperty("path")
	public String path;

	@JsonProperty("groupId")
	public String groupId;

	@JsonProperty("artifactId")
	public String artifactId;

	@JsonProperty("prefix")
	public String prefix;

	@JsonProperty("version")
	public String version;
}
