package com.boyarsky.apiservice.service;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ExportImportService {

    Resource exportAllMeasurements(Long userId);

    void importMeasurements(Long userId, MultipartFile file);
}
