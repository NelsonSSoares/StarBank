package com.github.nelsonssoares.userapi.file.importer.impl;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVImporter implements FileImporter {

    @Override
    public List<UserDTO> importFile(InputStream inputStream) throws Exception {

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable <CSVRecord> records = format.parse(new InputStreamReader(inputStream));

        return parseRecordsToUserDTO(records);

    }

    private List<UserDTO> parseRecordsToUserDTO(Iterable<CSVRecord> records) {

        List<UserDTO> usersDto = new ArrayList<>();

        for (CSVRecord record : records) {
            UserDTO user = new UserDTO();
            user.setName(record.get("name"));
            user.setLastName(record.get("last_name"));
            user.setCpf(record.get("cpf"));
            user.setEmail(record.get("email"));
            user.setPhone(record.get("phone"));
            user.setAccount(record.get("account"));
            user.setAgency(record.get("agency"));
            user.setPhoto(record.get("photo"));
            usersDto.add(user);
        }
        return usersDto;
    }
}
