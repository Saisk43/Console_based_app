import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchService {
    private static MatchService instance;
    private Map<String, Match> matches;

    public MatchService() {
        this.matches = new HashMap<>();
    }

    public static MatchService getInstance() {
        if (instance == null)
            instance = new MatchService();
        return instance;
    }

    public void addMatch(Match match) {
        matches.put(match.getId(), match);
    }

    public Match getMatch(String matchId) {
        return matches.get(matchId);
    }

    public List<Match> allMatchs() {
        return new ArrayList<>(matches.values());
    }

    public void updateMatchStatus(String matchId, MatchStatus status) {
        Match match = matches.get(matchId);
        if (match != null) {
            match.setStatus(status);
        }
    }
}
