package kcl.group.api.backendservice.service.impl;

import kcl.group.api.backendservice.config.FileLocationConfig;
import kcl.group.api.backendservice.exception.FileException;
import kcl.group.api.backendservice.service.FileStoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FileStoreServiceImpl implements FileStoreService {

    private static final Logger logger = getLogger(FileStoreServiceImpl.class);
    private final Path rootDirectory;
    @Autowired
    public FileStoreServiceImpl(FileLocationConfig fileLocationConfig) throws FileException {
        this.rootDirectory = Paths.get(fileLocationConfig.getUploadDirectory());

        try {
            Files.createDirectories(rootDirectory);
        } catch (IOException e) {
            throw new FileException("Could not create directory", e);
        }
    }

    @Override
    public String saveFile(MultipartFile multipartFile) throws FileException {
        if(multipartFile.isEmpty()){
            throw new FileException("Cannot save empty File" + multipartFile.getOriginalFilename());
        }
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if(filename.contains("..")){
            throw new FileException("Cannot save file outside directory!" + multipartFile.getOriginalFilename());
        }

        logger.info("File upload service started.....");
        try {
            Path fileDestination = this.rootDirectory.resolve(filename);
            InputStream inputStream = multipartFile.getInputStream();
            Files.copy(inputStream, fileDestination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileException("Cannot Read File contents", e);
        }
        return filename;

    }
}
