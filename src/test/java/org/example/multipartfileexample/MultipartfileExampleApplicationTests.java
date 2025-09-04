package org.example.multipartfileexample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

@AutoConfigureMockMvc
@SpringBootTest
@Import(MockMvcClientConfiguration.class)
class MultipartfileExampleApplicationTests {

    @Autowired
    private MultipartfileExampleApplication.OperationsClient operationsClient;

    @Test
    void body() {
        var message = operationsClient.body("Hello World");
        Assertions.assertEquals("Hey are here with a body request", message);
    }

    @Test
    void file() {
        var multipartFile = new MockMultipartFile("file", "hello.txt", "text/plain", "Hello World".getBytes());
        var message = operationsClient.multipart(multipartFile);
        Assertions.assertEquals("Hey are here with a multipart request", message);
    }


}
