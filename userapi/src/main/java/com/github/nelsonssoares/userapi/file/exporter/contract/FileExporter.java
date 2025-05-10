package com.github.nelsonssoares.userapi.file.exporter.contract;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<UserDTO> userDTOList);
}
