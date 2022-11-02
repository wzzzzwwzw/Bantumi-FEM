package es.upm.miw.bantumi.GameModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * FROM game")
    List<Game> getAllGames();

    @Query("SELECT * FROM game ORDER BY tokens DESC LIMIT 10 ")
    List<Game> getTop10Games();

    @Insert
    void insert(Game game);

    @Delete
    void delete(Game game);

    @Query("DELETE FROM game")
    void deleteAll();

    @Query("SELECT * FROM game WHERE winner == :player")
    List<Game> filterByPlayer(String player);

    @Query("SELECT DISTINCT winner FROM game")
    List<String> getPlayers();
}