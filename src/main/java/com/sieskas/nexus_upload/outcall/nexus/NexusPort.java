package com.sieskas.nexus_upload.outcall.nexus;

import com.sieskas.nexus_upload.model.UploadRequest;

public interface NexusPort {

  void upload(UploadRequest uploadRequest);
}
