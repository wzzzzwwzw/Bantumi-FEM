package es.upm.miw.bantumi.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.utils.StorageFiles;

public class RestoreDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();
        StorageFiles sf = new StorageFiles(main);
        String fileName = getResources().getString(R.string.saveFileName);
        String[] items = this.formatGames(sf.getAllLines(fileName));

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(R.string.txtDialogoRestoreTitulo)
                .setItems(items,
                        (dialog, which) -> {
                            main.deserialiseGame(items[which]);
                            sf.deleteLine(fileName, items[which]);
                        }
                )
                .setNegativeButton(
                        R.string.txtDialogoCancel,
                        (dialog, which) -> dismiss()
                );
        return builder.create();
    }

    String[] formatGames(String[] items) {
        List<String> reformatted = new ArrayList<>();
        for (String item : items) {
            String[] strings = item.split(";");
            String sb = "(" + strings[2] + ") " +
                    "Turno: " + strings[0] + "\n" +
                    strings[1];
            reformatted.add(sb);
        }
        return reformatted.toArray(new String[0]);
    }
}
