package com.github.nelsonssoares.userapi.outlayers.entrypoints;

import com.github.nelsonssoares.userapi.domain.dtos.UploadFileResponseDTO;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.docs.FileControllerDocs;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.API_BASE_URL;

@RestController
@RequestMapping(value = API_BASE_URL)
public class FileController implements FileControllerDocs {
    // POST OU PATCH??
    @PostMapping
    @Override
    public UploadFileResponseDTO uploadFile(MultipartFile file, Integer id) throws IOException {
        return null;
    }

    @GetMapping
    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
