package kcl.group.api.backendservice.controller;

import kcl.group.api.backendservice.exception.FileException;
import kcl.group.api.backendservice.exception.ResouceFileNotFoundException;
import kcl.group.api.backendservice.model.FileDto;
import kcl.group.api.backendservice.service.FileStoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * <p>
 * This class implements web interaction.
 * <p>
 *
 * @author : KCL Benediction Group
 **/

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

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws ResouceFileNotFoundException {
        logger.info("File download started..... {}", filename);

        Resource resource = fileStoreService.downloadFileAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename()
                        + "\"" ).body(resource);
    }


}
