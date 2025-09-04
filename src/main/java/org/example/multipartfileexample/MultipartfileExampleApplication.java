package org.example.multipartfileexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class MultipartfileExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipartfileExampleApplication.class, args);
    }

    @HttpExchange
    interface Operations {
        @PostExchange(value = "multipart", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
        String multipart(@RequestPart("file") MultipartFile multipartFile);

        @PostExchange
        String body(@RequestBody String something);
    }

    interface OperationsClient extends Operations {
    }

    @Slf4j
    @RestController
    static class MultipartController implements Operations {

        @Override
        public String multipart(MultipartFile multipartFile) {
            var message = "Hey are here with a multipart request";
            log.info(message);
            return message;
        }

        @Override
        public String body(String something) {
            var message = "Hey are here with a body request";
            log.info(message);
            return message;
        }
    }

    @Bean
    OperationsClient clientCredentialsOCRClient(RestClient.Builder restClientBuilder,
                                                ClientHttpRequestFactory requestFactory,
                                                ConversionService conversionService) {
        RestClient restClient = restClientBuilder.baseUrl("http://localhost:8080")
                .requestFactory(requestFactory)
                .build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .conversionService(conversionService)
                .build()
                .createClient(OperationsClient.class);
    }


}
