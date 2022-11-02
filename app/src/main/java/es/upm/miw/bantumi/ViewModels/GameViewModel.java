package es.upm.miw.bantumi.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import es.upm.miw.bantumi.GameModel.Game;
import es.upm.miw.bantumi.GameModel.GameRepository;


public class GameViewModel extends AndroidViewModel {

    GameRepository gameRepository;

    public GameViewModel(Application application) {
        super(application);
        this.gameRepository = new GameRepository(application);
    }

    public List<Game> getAllGames() {
        return this.gameRepository.getAllGames();
    }

    public List<Game> getTop10Games() {
        return this.gameRepository.getTop10Games();
    }

    public void insert(Game game) {
        this.gameRepository.insert(game);
    }

    public void deleteAll() {
        this.gameRepository.deleteAll();
    }

    public List<Game> filterByPlayer(String player) {
        return this.gameRepository.filterByPlayer(player);
    }

    public List<String> getPlayers() {
        return this.gameRepository.getPlayers();
    }
}