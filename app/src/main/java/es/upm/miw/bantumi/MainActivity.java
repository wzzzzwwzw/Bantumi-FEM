package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.ResourceBundle;

import es.upm.miw.bantumi.GameModel.Game;
import es.upm.miw.bantumi.ViewModels.BantumiViewModel;
import es.upm.miw.bantumi.ViewModels.GameViewModel;
import es.upm.miw.bantumi.dialogs.FinalAlertDialog;
import es.upm.miw.bantumi.dialogs.ResetDialog;
import es.upm.miw.bantumi.dialogs.RestoreDialog;
import es.upm.miw.bantumi.utils.StorageFiles;

public class MainActivity extends AppCompatActivity {

    protected final String LOG_TAG = "MiW";
    public JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    int numInicialSemillas;
    private SharedPreferences root_preferences;
    GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        numInicialSemillas = Integer.parseInt(root_preferences.getString(
                getString(R.string.key_token_num),
                getString(R.string.intNumInicialSemillas)));
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        gameViewModel = new GameViewModel(getApplication());
        crearObservadores();
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos   posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.opcAjustes: // @todo Preferencias
//                startActivity(new Intent(this, BantumiPrefs.class));
//                return true;
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
            case R.id.opcReiniciarPartida:
                new ResetDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                this.saveGame();
                return true;
            case R.id.opcRecuperarPartida:
                this.restoreGame();
                return true;
            // @TODO!!! resto opciones

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    private void restoreGame() {
        new RestoreDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    /**
     * Acción que se ejecuta al pulsar sobre un hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String texto = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? "Gana Jugador 1"
                : "Gana Jugador 2";
        Snackbar.make(
                        findViewById(android.R.id.content),
                        texto,
                        Snackbar.LENGTH_LONG
                )
                .show();

        String winner = (texto.contains("1") ? this.getPlayer1Name() : getString(R.string.txtPlayer2));
        int tokens = (texto.contains("1") ? this.juegoBantumi.getSemillas(6) : this.juegoBantumi.getSemillas(13));
        Game game = new Game(winner, tokens);
        gameViewModel.insert(game);
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    private String getPlayer1Name() {
        return root_preferences.getString(
                getString(R.string.key_player1),
                getString(R.string.txtPlayer1)
        );
    }

    public void resetGame(@NonNull View view) {
        new ResetDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    void saveGame() {
        String text = this.juegoBantumi.serializa();
        this.updateSavedGamesFile(text);
        Toast.makeText(this, getResources().getString(R.string.savedGame),
                Toast.LENGTH_SHORT).show();
    }

    private void updateSavedGamesFile(String text) {
        StorageFiles sf = new StorageFiles(this);
        String fileName = getResources().getString(R.string.saveFileName);
        String[] fileContent = sf.getAllLines(fileName);
        if (fileContent.length >= 5) {
            sf.deleteLine(fileName, fileContent[0]);
        }
        sf.update(fileName, text + '\n');
    }

    public void initialiseGame() {

        this.numInicialSemillas = this.getInitialTokens();
        this.juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        this.juegoBantumi.inicializar(JuegoBantumi.Turno.turnoJ1);
    }

    private int getInitialTokens() {


        return Integer.parseInt(root_preferences.getString(
                getString(R.string.key_token_num),
                getString(R.string.intNumInicialSemillas)));
    }

    public void deserialiseGame(String item) {

        this.juegoBantumi.deserializa(item);
        Toast.makeText(this, getResources().getString(R.string.restoreOk),
                Toast.LENGTH_SHORT).show();
    }
}