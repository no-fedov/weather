package org.nefedov.weather.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.CoordinateRequestDto;
import org.nefedov.weather.application.dto.WeatherClientResponseDto;
import org.nefedov.weather.application.exception.ApiConnectTimeoutException;
import org.nefedov.weather.application.exception.ApiResponseDeserializeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherClient {

    @Value("${apiKey}")
    private String apiKey;

    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    public Optional<WeatherClientResponseDto> findByCityName(String city) {
        Map<String, Object> parameters = Map.of("q", city);
        return sendRequest(parameters);
    }

    public Optional<WeatherClientResponseDto> findByCoordinate(CoordinateRequestDto dto) {
        Map<String, Object> parameters = Map.of("lat", dto.lat(), "lon", dto.lon());
        return sendRequest(parameters);
    }

    private Optional<WeatherClientResponseDto> sendRequest(Map<String, Object> parameters) {
        HttpRequest request = buildGetRequest(uri(parameters));
        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                return Optional.empty();
            }
            return Optional.of(deserializeResponseBody(response.body()));
        } catch (HttpConnectTimeoutException e) {
            throw new ApiConnectTimeoutException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WeatherClientResponseDto deserializeResponseBody(InputStream body) {
        try {
            return jsonMapper.readValue(body, new TypeReference<>() {
            });
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

    private URI uri(Map<String, Object> queryParam) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://api.openweathermap.org/data/2.5/weather");
        queryParam.forEach(builder::queryParam);
        builder.queryParam("units", "metric");
        builder.queryParam("appid", apiKey);
        return builder.build().toUri();
    }
}
