package es.upm.miw.bantumi.GameModel;

import android.app.Application;

import java.util.List;

public class GameRepository {

    private GameDAO gameDAO;

    public GameRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        this.gameDAO = database.getGameDAO();
    }

    public List<Game> getAllGames() {
        return this.gameDAO.getAllGames();
    }

    public List<Game> getTop10Games() {
        return this.gameDAO.getTop10Games();
    }

    public void insert(Game game) {
        GameDatabase.databaseWriteExecutor.execute(() -> {
            this.gameDAO.insert(game);
        });
    }

    public void delete(Game game) {
        this.gameDAO.delete(game);
    }

    public void deleteAll() {
        this.gameDAO.deleteAll();
    }

    public List<Game> filterByPlayer(String player) {
        return this.gameDAO.filterByPlayer(player);
    }

    public List<String> getPlayers() {
        return this.gameDAO.getPlayers();
    }
}