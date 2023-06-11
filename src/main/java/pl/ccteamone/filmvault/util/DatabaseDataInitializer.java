package pl.ccteamone.filmvault.util;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.ccteamone.filmvault.movie.dto.ApiMovieDtoPage;
import pl.ccteamone.filmvault.movie.dto.MovieDto;
import pl.ccteamone.filmvault.movie.mapper.MovieMapper;
import pl.ccteamone.filmvault.movie.service.MovieApiService;
import pl.ccteamone.filmvault.movie.service.MovieService;
import pl.ccteamone.filmvault.tvseries.dto.ApiTvSeriesDtoList;
import pl.ccteamone.filmvault.tvseries.dto.TvSeriesDto;
import pl.ccteamone.filmvault.tvseries.service.TvSeriesApiService;
import pl.ccteamone.filmvault.tvseries.service.TvSeriesService;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseDataInitializer implements CommandLineRunner {
    private final TvSeriesApiService tvSeriesApiService;
    private final TvSeriesService tvSeriesService;
    private final MovieApiService movieApiService;
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    private void updateDatabaseRecords() {
        List<MovieDto> movieUpdate = supplyDiscoverMovieDtoList();
        List<TvSeriesDto> tvSeriesUpdate = supplyDiscoverTvSeriesDtoList();

        if(!movieUpdate.isEmpty()) {
            for (int i = 0; i < movieUpdate.size(); i++) {
                MovieDto movie = movieUpdate.get(i);
                log.info("wywalam loopa " + movie.getTitle());
                if(movie.getApiID() != null && !movieService.existsByApiID(movie.getApiID())) {
                    movie = movieApiService.getApiMovie(movie.getApiID());
                    movieService.createMovie(movie);
                }
            }
        }

        if(!tvSeriesUpdate.isEmpty()) {
            for (int i = 0; i < tvSeriesUpdate.size(); i++) {
                TvSeriesDto tvSeries = tvSeriesUpdate.get(i);
                log.info("wywalam loopa " + tvSeries.getName());
                if(tvSeries.getId() != null && !tvSeriesService.existsByApiID(tvSeries.getId())) {
                    tvSeries = tvSeriesApiService.getApiTvSeries(tvSeries.getId());
                    tvSeriesService.createTvSeries(tvSeries);
                }
            }
        }

        log.info("Auto Update Method: finished");
    }

    private List<MovieDto> supplyDiscoverMovieDtoList() {
        List<MovieDto> movieUpdateList = new ArrayList<>();
        ApiMovieDtoPage page;
        for (int i = 1; i < 11; i++) {
            page = movieApiService.getMovieDiscoverPage(i);
            if(page == null) {
                break;
            }
            log.info("" + page.getPage());
            log.info("" + page.getMovies().get(0).getTitle());
            movieUpdateList.addAll(movieMapper.mapToMovieDtoList(page.getMovies()));
        }
        return movieUpdateList;
    }

    private List<TvSeriesDto> supplyDiscoverTvSeriesDtoList() {
        List<TvSeriesDto> tvSeriesUpdateList = new ArrayList<>();
        ApiTvSeriesDtoList page;
        for (int i = 1; i < 11; i++) {
            page = tvSeriesApiService.getTvSeriesDiscoverPage(i);
            if(page == null) {
                break;
            }
            tvSeriesUpdateList.addAll(page.getTvSeries());
        }
        return tvSeriesUpdateList;


    }





    @Override
    public void run(String... args) throws Exception {
        //updateDatabaseRecords();
    }
}
