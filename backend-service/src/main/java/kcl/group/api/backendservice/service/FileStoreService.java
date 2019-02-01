package kcl.group.api.backendservice.service;

import kcl.group.api.backendservice.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {


    String saveFile(MultipartFile multipartFile) throws FileException;

}
