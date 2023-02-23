package com.sieskas.nexus_upload.v1.mapper;

import com.sieskas.nexus_upload.model.UploadRequest;
import com.sieskas.nexus_upload.v1.controller.resources.UploadRequestResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UploadMapper {
  UploadRequest toModel(UploadRequestResource requestResource);
}
