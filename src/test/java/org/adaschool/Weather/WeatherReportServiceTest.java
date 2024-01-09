package org.adaschool.Weather;


import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getWeatherReportTest() {
        double latitude = 40.7128;
        double longitude = -74.0060;
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=a57e55b3fbb041cc6fc4391081c8dcb4";

        WeatherApiResponse.Main mockMain = new WeatherApiResponse.Main();
        mockMain.setTemperature(0.0);
        mockMain.setHumidity(88.0);

        WeatherApiResponse mockResponse = new WeatherApiResponse();
        mockResponse.setMain(mockMain);

        // al no ser el rest template un bean sino que se está creando en cada ejecucion del metodo, no se aplica la configuracion de este when y se le pega a la API rel
        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(mockResponse);

        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(0.0, report.getTemperature());
        assertEquals(88.0, report.getHumidity());
    }
}
