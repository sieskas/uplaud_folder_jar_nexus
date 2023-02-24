package com.sieskas.nexus_upload.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileLibrary {

	public String path;
	public String version;
	public String name;
	public String extension;
}
