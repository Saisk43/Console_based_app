import java.time.LocalDateTime;
import java.util.List;

public class Match {
    private final String id;
    private final String matchName;
    private final String matchVenue;
    private final LocalDateTime startTime;
    private final List<Team> teams;
    private MatchStatus status;
    private Scorecard scoreCard;

    public Match(String id, String matchName, String matchVenue, LocalDateTime startTime, List<Team> teams) {
        this.id = id;
        this.matchName = matchName;
        this.matchVenue = matchVenue;
        this.startTime = startTime;
        this.teams = teams;
        this.status = MatchStatus.SCHEDULED;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getMatchName() {
        return matchName;
    }

    public String getMatchVenue() {
        return matchVenue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
