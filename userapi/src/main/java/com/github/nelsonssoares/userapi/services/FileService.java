package com.github.nelsonssoares.userapi.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
 String storeFile(MultipartFile file, Integer id) throws IOException;
 Resource loadFileAsResource(String fileName, Integer id);

}
