package com.sieskas.nexus_upload.service;

import com.sieskas.nexus_upload.model.FileLibrary;
import com.sieskas.nexus_upload.model.UploadRequest;
import com.sieskas.nexus_upload.outcall.nexus.NexusPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements IUploadService {

  private final NexusPort nexusPort;

  public String upload(UploadRequest uploadRequest) {

    String prefix = uploadRequest.getPrefix() != null ? uploadRequest.getPrefix() + "-" : "";
    uploadRequest
        .getFiles()
        .forEach(fileLibrary -> fileLibrary.setName(prefix + fileLibrary.getName()));

    nexusPort.upload(uploadRequest);

    StringBuilder sb = new StringBuilder();

    for (FileLibrary fileLibrary : uploadRequest.getFiles()) {
      sb.append("<dependency>\n");
      sb.append("  <groupId>").append(uploadRequest.groupId).append("</groupId>\n");
      sb.append("  <artifactId>").append(fileLibrary.getName()).append("</artifactId>\n");
      sb.append("  <version>").append(fileLibrary.getVersion()).append("</version>\n");
      sb.append("</dependency>\n");
    }

    return sb.toString();
  }
}
