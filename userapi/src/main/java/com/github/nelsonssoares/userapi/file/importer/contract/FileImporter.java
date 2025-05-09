package com.github.nelsonssoares.userapi.file.importer.contract;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<UserDTO> importFile(InputStream inputStream) throws Exception;
}
