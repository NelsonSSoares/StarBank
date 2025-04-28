package com.github.nelsonssoares.userapi.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.configs.filesTransfer.FileStorageConfig;
import com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import com.github.nelsonssoares.userapi.exceptions.FileNotFoundException;
import com.github.nelsonssoares.userapi.exceptions.FileStorageException;
import com.github.nelsonssoares.userapi.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation;
    private final UserRepository repository;

    @Autowired
    public FileServiceImpl(FileStorageConfig fileStorageConfig, UserRepository repository, ObjectMapper mapper) {
        this.repository = repository;


        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        try{
            // cria o diretorio
            Files.createDirectories(this.fileStorageLocation);

        }catch (Exception e){

            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }


    @Override
    public String storeFile(MultipartFile file, Integer id) throws IOException {

      try{
          Files.createDirectories(fileStorageLocation);

          String originalFileName = file.getOriginalFilename();
          String fileName = "user_" + id + "_" + System.currentTimeMillis()+ "_" + originalFileName;
          Path targetLocation = fileStorageLocation.resolve(fileName);

          Files.copy(file.getInputStream(), targetLocation);

          User user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
          String photoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                  .path("/" + ControllersConstants.API_BASE_URL)
                  .path(ControllersConstants.FILES)
                  .path(ControllersConstants.DOWNLOAD_FILE.replace("{fileName:.+}", user.getPhoto()))
                  .toUriString();
          user.setPhoto(photoUrl);
          repository.save(user);
          return fileName;

      } catch (Exception e) {
          throw new RuntimeException(e);
      }

    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found "+ fileName ,ex);
        }
    }
}
