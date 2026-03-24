package integration.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import config.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.WeatherExternalResponseDto;
import org.nefedov.weather.application.dto.WeatherInternalResponseDto;
import org.nefedov.weather.application.service.LocationService;
import org.nefedov.weather.application.service.WeatherService;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {AppConfig.class, TestProperties.class})
@Transactional
public class WeatherServiceTest {

    private static final Integer userId = 1;
    private static final LocationDto firstLocation = new LocationDto(1,
            "Москва",
            new BigDecimal(1),
            new BigDecimal(1)
    );
    private static final LocationDto secondLocation = new LocationDto(2,
            "Тверь",
            new BigDecimal(2),
            new BigDecimal(2)
    );

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private LocationService locationService;

    @MockitoBean
    private HttpClient httpClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.uri.weather}")
    private String weatherURI;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void findUserWeathers() throws IOException, InterruptedException {
        Set<LocationDto> locations = Set.of(firstLocation, secondLocation);
        when(locationService.findUserLocation(userId)).thenReturn(locations);

        WeatherExternalResponseDto firstExternalWeather = getExternalWeather(firstLocation.name(),
                firstLocation.longitude(),
                firstLocation.latitude()
        );
        WeatherExternalResponseDto secondExternalWeather = getExternalWeather(secondLocation.name(),
                secondLocation.longitude(),
                secondLocation.latitude()
        );
        prepareMockResponse(firstExternalWeather);
        prepareMockResponse(secondExternalWeather);

        WeatherInternalResponseDto firstInternalWeather = getInternalWeather(firstLocation);
        WeatherInternalResponseDto secondInternalWeather = getInternalWeather(secondLocation);

        List<WeatherInternalResponseDto> userWeather = weatherService.findForUser(userId);

        assertTrue(userWeather.contains(firstInternalWeather));
        assertTrue(userWeather.contains(secondInternalWeather));
    }

    private HttpRequest buildGetRequest(String uri, BigDecimal latitude, BigDecimal longitude) {
        URI copletedUri = UriComponentsBuilder.fromUriString(uri)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .build()
                .toUri();
        return HttpRequest.newBuilder()
                .uri(copletedUri)
                .GET()
                .build();
    }

    private WeatherExternalResponseDto getExternalWeather(String cityName, BigDecimal longitude, BigDecimal latitude) {
        WeatherExternalResponseDto weather = new WeatherExternalResponseDto();
        weather.setCityName(cityName);
        weather.setCoordinate(new WeatherExternalResponseDto.Coordinate(longitude.toString(), latitude.toString()));
        return weather;
    }

    private WeatherInternalResponseDto getInternalWeather(LocationDto location) {
        return new WeatherInternalResponseDto(
                getExternalWeather(location.name(), location.longitude(), location.longitude()),
                location.id());
    }

    private void prepareMockResponse(WeatherExternalResponseDto weatherExternalResponseDto) throws IOException, InterruptedException {
        byte[] weatherAsByte = jsonMapper.writeValueAsBytes(weatherExternalResponseDto);
        InputStream inputStream = new ByteArrayInputStream(weatherAsByte);
        HttpResponse response = mock(HttpResponse.class);
        when(response.body()).thenReturn(inputStream);
        when(response.statusCode()).thenReturn(HttpStatus.OK.value());
        when(httpClient.send(argThat(resquest -> resquest.uri().), any())).thenReturn(response);
    }

}
