package pl.ccteamone.filmvault.tvseries.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.ccteamone.filmvault.tvseries.TvSeries;
import pl.ccteamone.filmvault.tvseries.dto.TvSeriesDto;

@Component
public class ApiTvSeriesClient {
        /*
    https://api.themoviedb.org/3/tv/11?api_key=2cf008cfced14e2935757fdbc052768b
     */

    private static final String API_TVSERIES_URL = "https://api.themoviedb.org/3/tv/";
    private static final String API_KEY = "2cf008cfced14e2935757fdbc052768b";

    private final RestTemplate restTemplate = new RestTemplate();

    public TvSeriesDto getApiTvSeriesForTvSeriesId(Long id) {
        TvSeriesDto tvSeriesDto = callGetMethod("{series_id}?api_key={apiKey}", TvSeriesDto.class, id, API_KEY);
        return TvSeriesDto.builder()
                .apiID (tvSeriesDto.getApiID())
                .name(tvSeriesDto.getName())
                .overview(tvSeriesDto.getOverview())
                .posterPath(tvSeriesDto.getPosterPath())
                .genre(tvSeriesDto.getGenre())
                .originCountry(tvSeriesDto.getOriginCountry())
                .firstAirDate(tvSeriesDto.getFirstAirDate())
                .lastAirDate(tvSeriesDto.getLastAirDate())
                .seasons(tvSeriesDto.getSeasons())
                .build();
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(API_TVSERIES_URL + url,
                responseType, objects);
    }
}
