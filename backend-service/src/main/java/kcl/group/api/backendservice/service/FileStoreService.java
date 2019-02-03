package kcl.group.api.backendservice.service;

import kcl.group.api.backendservice.exception.FileException;
import kcl.group.api.backendservice.exception.ResouceFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * This class handles all the logic required with file interaction.
 * <p>
 *
 * @author : KCL Benediction Group
 **/

public interface FileStoreService {


    String saveFile(MultipartFile multipartFile) throws FileException;
    Resource downloadFileAsResource(String filename) throws ResouceFileNotFoundException;
}
