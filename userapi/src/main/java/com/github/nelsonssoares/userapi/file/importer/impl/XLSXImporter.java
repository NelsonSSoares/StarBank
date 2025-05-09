package com.github.nelsonssoares.userapi.file.importer.impl;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@Component
public class XLSXImporter implements FileImporter {

    @Override
    public List<UserDTO> importFile(InputStream inputStream) throws Exception {

        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            var sheet = workbook.getSheetAt(0);
            var rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip header
            }

            return parseRowsToUserDTOList(rowIterator);

        }
    }

    private List<UserDTO> parseRowsToUserDTOList(Iterator<Row> rowIterator) {
        List<UserDTO> usersDto = new java.util.ArrayList<>();

        while (rowIterator.hasNext()) {
            var row = rowIterator.next();

            if (isValid(row)) {
                usersDto.add(parseRowToUserDTO(row));
            }
        }

        return usersDto;
    }

    private boolean isValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }

    private UserDTO parseRowToUserDTO(Row row) {

        var user = new UserDTO();
        user.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
        user.setName(row.getCell(1).getStringCellValue());
        user.setLastName(row.getCell(2).getStringCellValue());
        user.setCpf(row.getCell(3).getStringCellValue());
        user.setEmail(row.getCell(4).getStringCellValue());
        user.setPhone(row.getCell(5).getStringCellValue());
        user.setAccount(row.getCell(6).getStringCellValue());
        user.setAgency(row.getCell(7).getStringCellValue());
        user.setPhoto(row.getCell(8).getStringCellValue());
        return user;

    }


}
