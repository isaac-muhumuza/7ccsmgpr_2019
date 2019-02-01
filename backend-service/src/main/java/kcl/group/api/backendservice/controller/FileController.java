package kcl.group.api.backendservice.controller;

import kcl.group.api.backendservice.exception.FileException;
import kcl.group.api.backendservice.model.FileDto;
import kcl.group.api.backendservice.service.FileStoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class FileController {

    private static final Logger logger = getLogger(FileController.class);

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping("/upload")
    public FileDto uploadFile(@RequestParam("file") MultipartFile file) throws FileException {
        logger.info("File upload started..... {}", file.getOriginalFilename());
            String filename = fileStoreService.saveFile(file);
            return new FileDto(filename,file.getContentType(), file.getSize());


    }

}
