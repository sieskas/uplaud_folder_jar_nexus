package com.sieskas.nexus_upload.service;

import com.sieskas.nexus_upload.model.UploadRequest;

public interface IUploadService {
  void upload(UploadRequest toModel);
}
