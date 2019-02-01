package kcl.group.api.backendservice.service;

import kcl.group.api.backendservice.config.FileLocationConfig;
import kcl.group.api.backendservice.exception.FileException;
import kcl.group.api.backendservice.service.impl.FileStoreServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStoreServiceTest {

    private FileStoreService fileStoreService;
    private FileLocationConfig fileLocationConfig = new FileLocationConfig();

    @Before
    public void setUp(){
        fileLocationConfig.setUploadDirectory("target/files/" + Math.abs(new Random().nextLong()));
        try {
            fileStoreService = new FileStoreServiceImpl(fileLocationConfig);
        } catch (FileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveFile() throws FileException {
        String filename = fileStoreService.saveFile(new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "This is testing the service save method!".getBytes()));

        Assertions.assertThat(filename).isEqualToIgnoringCase("test.txt");

    }
}