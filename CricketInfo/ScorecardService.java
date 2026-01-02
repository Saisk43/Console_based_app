import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ScorecardService {
    private static ScorecardService instance;
    private Map<String, Scorecard> scorecards;
    private AtomicInteger scorecardIdCounter;

    public ScorecardService() {
        scorecards = new ConcurrentHashMap<>();
        scorecardIdCounter = new AtomicInteger();
    }

    public static ScorecardService getInstance() {
        if (instance == null)
            instance = new ScorecardService();
        return instance;
    }

    public void createScorecard(Match match) {
        String id = generateId(match.getId());
        Scorecard scorecard = new Scorecard(id, match);
        scorecards.put(id, scorecard);
    }

    public Scorecard searchScorecard(String scorecardId) {
        return scorecards.get(scorecardId);
    }

    public List<Scorecard> getAllScorecard() {
        return new ArrayList<>(scorecards.values());
    }

    public void updateScore(String scorecardId, String teamId, int Score) {
        Scorecard scorecard = scorecards.get(scorecardId);
        if (scorecard != null)
            scorecard.updateScore(teamId, Score);
    }

    public void addInnings(String scorecardId, Innings innings) {
        Scorecard scorecard = scorecards.get(scorecardId);
        if (scorecard != null)
            scorecard.addInnings(innings);
    }

    public String generateId(String matchId) {
        int id = scorecardIdCounter.incrementAndGet();
        System.out.println("SC-" + matchId + "-" + String.format("%04d", id));
        return "SC-" + matchId + "-" + String.format("%04d", id);
    }
}
