package es.upm.miw.bantumi.GameModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = Game.TABLE_NAME)
public class Game {
    static public final String TABLE_NAME = "game";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "winner")
    private String winner;
    @ColumnInfo(name = "tokens")
    private int tokens;
    @ColumnInfo(name = "date")
    private Date date;

    public Game(String winner, int tokens) {
        this.date = Calendar.getInstance().getTime();
        this.winner = winner;
        this.tokens = tokens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return this.tokens == game.tokens && Objects.equals(this.winner, game.winner) && Objects.equals(this.date, game.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.winner, this.tokens, this.date);
    }

    @Override
    public String toString() {
        return this.winner + " - " + this.tokens +
                " semillas \n" + this.formatDate(this.date);
    }
}