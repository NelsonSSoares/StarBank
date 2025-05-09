package com.github.nelsonssoares.userapi.file.importer.factory;

import com.github.nelsonssoares.userapi.file.importer.contract.FileImporter;
import com.github.nelsonssoares.userapi.file.importer.impl.CSVImporter;
import com.github.nelsonssoares.userapi.file.importer.impl.XLSXImporter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    private final ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception {
        logger.info("File name: {}", fileName);
        if (fileName.endsWith(".xlsx")) {
            logger.info("XLSX file detected");
            return context.getBean(XLSXImporter.class);
        } else if (fileName.endsWith(".csv")) {
            logger.info("CSV file detected");
            return context.getBean(CSVImporter.class);
        } else {
            throw new BadRequestException("File type not supported");
        }
    }
}
