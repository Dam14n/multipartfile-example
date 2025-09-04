package org.example.multipartfileexample;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class MockMvcClientConfiguration {
    @Bean
    @Primary
    ClientHttpRequestFactory mockMvcHttpRequestFactory(MockMvc mockMvc) {
        return new MockMvcClientHttpRequestFactory(mockMvc);
    }

    @Bean(
            name = {"mockMvcRestTemplate"}
    )
    RestTemplate mockMvcRestTemplate(RestTemplateBuilder restTemplateBuilder, ClientHttpRequestFactory httpRequestFactory) {
        return restTemplateBuilder.requestFactory(() -> httpRequestFactory).build();
    }
}
