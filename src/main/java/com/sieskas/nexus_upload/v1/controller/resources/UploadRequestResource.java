package com.sieskas.nexus_upload.v1.controller.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequestResource {

  public String path;
  public String groupId;
  public String artifactId;
  public String version;
}
