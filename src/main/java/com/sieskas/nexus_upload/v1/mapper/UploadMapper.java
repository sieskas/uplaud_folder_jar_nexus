package com.sieskas.nexus_upload.v1.mapper;

import com.sieskas.nexus_upload.model.FileLibrary;
import com.sieskas.nexus_upload.model.UploadRequest;
import com.sieskas.nexus_upload.v1.controller.resources.UploadRequestResource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class UploadMapper {

	@Mapping(source = "path", target = "files", qualifiedByName = "findFiles")
	public abstract UploadRequest toModel(UploadRequestResource requestResource);

	@Named("findFiles")
	protected List<FileLibrary> getFileLibraries(String path) {
		List<FileLibrary> fileLibraries = new ArrayList<>();
		File directory = new File(path);
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Invalid directory path: " + path);
		}
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				FileLibrary fileLibrary = FileLibrary.builder().build();
				fileLibrary.setName(file.getName());
				fileLibrary.setPath(file.getAbsolutePath());
				String extension = getFileExtension(file);
				fileLibrary.setExtension(extension);
				String fileNameWithoutExtension = removeFileExtension(file.getName());
				fileLibrary.setVersion(getVersionFromFileName(fileNameWithoutExtension));
				fileLibrary.setName(extractName(fileNameWithoutExtension));
				fileLibraries.add(fileLibrary);
			}
		}
		return fileLibraries;
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOfDot = name.lastIndexOf(".");
		if (lastIndexOfDot == -1) {
			return ""; // no extension found
		}
		return name.substring(lastIndexOfDot + 1);
	}

	private String removeFileExtension(String fileName) {
		int lastIndexOfDot = fileName.lastIndexOf(".");
		if (lastIndexOfDot == -1) {
			return fileName; // no extension found
		}
		return fileName.substring(0, lastIndexOfDot);
	}

	private String getVersionFromFileName(String fileName) {
		Pattern pattern = Pattern.compile("-\\d+(\\.\\d+)*[a-zA-Z-]*");
		Matcher matcher = pattern.matcher(fileName);
		while (matcher.find()) {
			String version = matcher.group().substring(1);
			if (version.matches("^\\d+(\\.\\d+)*(-[a-zA-Z0-9]+)*$")) {
				return version;
			}
		}
		return "1.0.0";
	}

	private String extractName(String str) {
		Pattern pattern = Pattern.compile("^.*?(?=-\\d)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group();
		}
		return str;
	}
}
