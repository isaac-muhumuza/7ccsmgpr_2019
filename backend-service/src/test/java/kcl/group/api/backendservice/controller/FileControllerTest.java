package kcl.group.api.backendservice.controller;

import kcl.group.api.backendservice.service.FileStoreService;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileControllerTest {

    @MockBean
    private FileStoreService fileStoreService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "testFile.txt",
                "text/plain", "This is a test for the upload function!".getBytes());
        MvcResult result = this.mockMvc.perform(fileUpload("/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();
        then(this.fileStoreService).should().saveFile(multipartFile);

        AbstractStringAssert<?> equalTo = Assertions.assertThat(result.getResponse().getContentAsString()).containsIgnoringCase("\"fileSize\":"+multipartFile.getSize());
    }

}