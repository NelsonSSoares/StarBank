package com.github.nelsonssoares.userapi.file.exporter.impl;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.file.exporter.contract.FileExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class XLSXExporter implements FileExporter {

    @Override
    public Resource exportFile(List<UserDTO> userDTOList) {

        try(Workbook workbook = new XSSFWorkbook()){

            Sheet sheet = workbook.createSheet("Users");

            // Criar o cabeçalho da planilha
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Account", "Agency", "Cpf", "Creation Date", "Email", "Name", "Last Name","Modification Date", "Phone", "Photo"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }
            int rowIndex = 1;
            // Preencher os dados
            for (UserDTO dto : userDTOList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(dto.getId());
                row.createCell(1).setCellValue(dto.getAccount());
                row.createCell(2).setCellValue(dto.getAgency());
                row.createCell(3).setCellValue(dto.getCpf());
                row.createCell(5).setCellValue(dto.getEmail());
                row.createCell(6).setCellValue(dto.getName());
                row.createCell(7).setCellValue(dto.getLastName());
                row.createCell(9).setCellValue(dto.getPhone());
                row.createCell(10).setCellValue(dto.getPhoto());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
