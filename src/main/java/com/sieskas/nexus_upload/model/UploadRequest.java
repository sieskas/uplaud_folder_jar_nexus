package com.sieskas.nexus_upload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest {

  public String path;
  public String groupId;
  public String artifactId;
  public String version;
}
