package es.upm.miw.bantumi.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import es.upm.miw.bantumi.GameModel.Game;
import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.ViewModels.GameViewModel;
import es.upm.miw.bantumi.dialogs.DeleteAllDialog;
import es.upm.miw.bantumi.dialogs.FilterByPlayerDialog;

public class RankingActivity extends AppCompatActivity {

    ListView gameListView;
    GameViewModel gameViewModel;
    ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.gameViewModel = new GameViewModel(getApplication());

        this.gameListView = findViewById(R.id.lvRanking);
        this.getTop10Games();
    }

    public void getTop10Games() {
        this.adapter = new ArrayAdapter<>(this,
                R.layout.listview_item,
                R.id.tvRanking, this.gameViewModel.getTop10Games());
        this.gameListView.setAdapter(adapter);
        this.adapter.notifyDataSetChanged();
    }

    public void clear(View view) {
        this.getTop10Games();
    }

    public void deleteAll(View view) {
        new DeleteAllDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    public void deleteAll() {
        this.gameViewModel.deleteAll();
        this.adapter.clear();
        this.adapter.notifyDataSetChanged();
    }

    public void filterByPlayer(View view) {
        new FilterByPlayerDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    public void filterByPlayer(String player) {
        this.adapter.clear();
        this.adapter.addAll(
                this.gameViewModel.filterByPlayer(player)
        );
        this.adapter.notifyDataSetChanged();
    }

    public String[] getPlayerArray() {
        List<String> players = this.gameViewModel.getPlayers();
        return players.toArray(new String[0]);
    }
}