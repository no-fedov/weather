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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
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
        Set<LocationDto> locations = Set.of(firstLocation);
        when(locationService.findUserLocation(userId)).thenReturn(locations);

        WeatherExternalResponseDto externalWeather = getExternalWeather(
                firstLocation.name(),
                firstLocation.longitude(),
                firstLocation.latitude()
        );

        prepareMockResponse(externalWeather);

        List<WeatherInternalResponseDto> userWeather = weatherService.findForUser(userId);

        assertFalse(userWeather.isEmpty());
        assertEquals(firstLocation.id(), userWeather.getFirst().getUserLocationId());
        assertEquals(firstLocation.longitude().toString(), userWeather.getFirst().getWeather().getCoordinate().getLon());
        assertEquals(firstLocation.latitude().toString(), userWeather.getFirst().getWeather().getCoordinate().getLat());
    }

    private WeatherExternalResponseDto getExternalWeather(String cityName, BigDecimal longitude, BigDecimal latitude) {
        WeatherExternalResponseDto weather = new WeatherExternalResponseDto();
        weather.setCityName(cityName);
        weather.setCoordinate(new WeatherExternalResponseDto.Coordinate(longitude.toString(), latitude.toString()));
        return weather;
    }

    private void prepareMockResponse(WeatherExternalResponseDto weatherExternalResponseDto) throws IOException, InterruptedException {
        byte[] weatherAsByte = jsonMapper.writeValueAsBytes(weatherExternalResponseDto);
        InputStream inputStream = new ByteArrayInputStream(weatherAsByte);
        HttpResponse response = mock(HttpResponse.class);
        when(response.body()).thenReturn(inputStream);
        when(response.statusCode()).thenReturn(HttpStatus.OK.value());
        when(httpClient.send(any(), any())).thenReturn(response);
    }

}
