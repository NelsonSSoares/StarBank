package com.github.nelsonssoares.userapi.outlayers.entrypoints;

import com.github.nelsonssoares.userapi.domain.dtos.UploadFileResponseDTO;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.docs.FileControllerDocs;
import com.github.nelsonssoares.userapi.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.*;
import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.API_DESCRIPTION;

@Tag(name = API_TAG_FILES, description = API_DESCRIPTION)
@RestController
@RequestMapping(value = API_BASE_URL + FILES )
@RequiredArgsConstructor
public class FileController implements FileControllerDocs {

    private final FileService fileService;

    // POST OU PATCH??
    @PostMapping(value = ID)
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("files") MultipartFile files,@PathVariable Integer id) throws IOException {
        var fileName = fileService.storeFile(files, id);


        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_BASE_URL + FILES)
                .path(API_BASE_URL+FILES+DOWNLOAD)
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDTO(fileName, fileDownloadUri, files.getContentType(), files.getSize());
    }

    @GetMapping(value = DOWNLOAD_FILE)
    @Override
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return null;
    }
}
