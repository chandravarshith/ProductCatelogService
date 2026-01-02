package org.example.productcatelogservice.utils;

import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {
    @Autowired
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = this.restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public <T> boolean isValidResponse(ResponseEntity<T> responseEntity, Class<T> responseType, HttpStatusCode happyStatusCode) {
        if(responseEntity.getStatusCode().equals(happyStatusCode)
                && responseEntity.hasBody()){
            return true;
        }
        return false;
    }
}
