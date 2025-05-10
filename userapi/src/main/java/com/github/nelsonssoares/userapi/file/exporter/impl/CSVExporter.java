package com.github.nelsonssoares.userapi.file.exporter.impl;


import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CSVExporter implements FileExporter {

    @Override
    public Resource exportFile(List<UserDTO> userDTOList) {
        return null;
    }
}
