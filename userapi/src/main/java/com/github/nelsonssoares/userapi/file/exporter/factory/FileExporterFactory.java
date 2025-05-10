package com.github.nelsonssoares.userapi.file.exporter.factory;

import com.github.nelsonssoares.userapi.file.exporter.MyMediaTypes;
import com.github.nelsonssoares.userapi.file.exporter.contract.FileExporter;
import com.github.nelsonssoares.userapi.file.exporter.impl.CSVExporter;
import com.github.nelsonssoares.userapi.file.exporter.impl.XLSXExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class FileExporterFactory {

    private final ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MyMediaTypes.APPLICATION_XLSX)) {
            return context.getBean(XLSXExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MyMediaTypes.APPLICATION_CSV)) {
            return context.getBean(CSVExporter.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not supported");
        }
    }

}
