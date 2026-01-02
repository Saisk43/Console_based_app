import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String id;
    private final String teamName;
    private List<Player> teamPlayers;

    public Team(String id, String teamName) {
        this.id = id;
        this.teamName = teamName;
        this.teamPlayers = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (this.teamPlayers.size() < 12)
            teamPlayers.add(player);
    }

    public void addPlayer(List<Player> player) {
        if (this.teamPlayers.size() + player.size() <= 12)
            teamPlayers.addAll(player);
        else
            System.out.println((this.teamPlayers.size() + player.size()) - 12 + "players are excess");
    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }
}
