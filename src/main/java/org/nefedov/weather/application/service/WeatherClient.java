package org.nefedov.weather.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationResponseDto;
import org.nefedov.weather.application.dto.WeatherResponseDto;
import org.nefedov.weather.application.exception.ApiConnectTimeoutException;
import org.nefedov.weather.application.exception.ApiNotFoundException;
import org.nefedov.weather.application.exception.ApiResponseDeserializeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
@Profile("dev")
@RequiredArgsConstructor
public class WeatherClient {

    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.uri.geo}")
    private String geoURI;
    @Value("${weather.api.uri.weather}")
    private String weatherURI;

    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    public List<LocationResponseDto> findLocationByCityName(String city) {
        Map<String, Object> parameters = Map.of("q", city, "limit", 5);
        return sendRequest(geoURI, parameters, new TypeReference<>() {});
    }

    public WeatherResponseDto findByCoordinate(BigDecimal latitude, BigDecimal longitude) {
        Map<String, Object> parameters = Map.of("lat", latitude, "lon", longitude, "units", "metric");
        return sendRequest(weatherURI, parameters, new TypeReference<>() {});
    }

    private <T> T sendRequest(String uri, Map<String, Object> parameters, TypeReference<T> typeReference) {
        HttpRequest request = buildGetRequest(uri(uri, parameters));
        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new ApiNotFoundException();
            }
            return deserializeResponseBody(response.body(), typeReference);
        } catch (HttpConnectTimeoutException e) {
            throw new ApiConnectTimeoutException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T deserializeResponseBody(InputStream body, TypeReference<T> typeReference) {
        try {
            return jsonMapper.readValue(body, typeReference);
        } catch (IOException e) {
            throw new ApiResponseDeserializeException(e);
        }
    }

    private HttpRequest buildGetRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }

    private URI uri(String uri, Map<String, Object> queryParam) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
        queryParam.forEach(builder::queryParam);
        builder.queryParam("appid", apiKey);
        return builder.build().toUri();
    }
}
