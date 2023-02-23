package com.sieskas.nexus_upload.v1.mapper;

import com.sieskas.nexus_upload.model.FileLibrary;
import com.sieskas.nexus_upload.model.UploadRequest;
import com.sieskas.nexus_upload.v1.controller.resources.UploadRequestResource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UploadMapper {

  @Mapping(source = "path", target = "files", qualifiedByName = "findFiles")
  UploadRequest toModel(UploadRequestResource requestResource);

  @Named("findFiles")
  default List<FileLibrary> getFilesInfo(String path) {
    List<FileLibrary> result = new ArrayList<>();

    File directory = new File(path);

    if (directory.exists() && directory.isDirectory()) {
      File[] files = directory.listFiles();

      for (File file : files) {
        String fileName = file.getName();

        int extensionIndex = fileName.lastIndexOf(".");
        String nameWithoutExtension =
            extensionIndex > 0 ? fileName.substring(0, extensionIndex) : fileName;

        String[] nameAndVersion = nameWithoutExtension.split("-", 2);
        String name = nameAndVersion[0];
        String version = nameAndVersion.length > 1 ? nameAndVersion[1] : "1.0.0";

        result.add(
            FileLibrary.builder()
                .path(file.getAbsolutePath())
                .version(version)
                .name(name)
                .extension(extensionIndex > 0 ? fileName.substring(extensionIndex) : "")
                .build());
      }
    }

    return result;
  }
}
