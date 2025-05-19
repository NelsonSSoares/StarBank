package com.github.nelsonssoares.userapi.file.exporter.impl;


import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CSVExporter implements FileExporter {

    @Override
    public Resource exportFile(List<UserDTO> peopleDTOList) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "First Name", "Last Name", "Address", "Gender", "Enabled")
                .setSkipHeaderRecord(false)
                .build();

        try(CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {

            for(UserDTO dto : peopleDTOList) {
                csvPrinter.printRecord(
                        dto.getId(),
                        dto.getName(),
                        dto.getLastName(),
                        dto.getCpf(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getPhoto(),
                        dto.getAccount(),
                        dto.getAgency()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }
}
