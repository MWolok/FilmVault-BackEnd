package pl.ccteamone.filmvault.tvseries.dto;


import lombok.Builder;
import lombok.Data;
import pl.ccteamone.filmvault.region.Region;
import pl.ccteamone.filmvault.vodplatform.dto.VODPlatformDto;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class TvSeriesDto {
    private Long id;
    private String name;
    private String overview;
    private String posterPath;
    private String genres;
    private boolean adult;
    private String origin;
    private LocalDate firstAirDate;
    private LocalDate lastAirDate;
    private int seasons;
    private int episodes;
    private Long apiID;

    private Region region;
    private Set<VODPlatformDto> vodPlatforms;

}
