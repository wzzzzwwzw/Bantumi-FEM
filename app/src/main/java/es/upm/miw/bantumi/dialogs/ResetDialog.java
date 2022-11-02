package es.upm.miw.bantumi.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;

public class ResetDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(R.string.txtDialogoResetTitulo)
                .setMessage(R.string.txtDialogoResetPregunta)
                .setPositiveButton(
                        R.string.txtDialogoAfirmativo,
                        (dialog, which) -> main.initialiseGame() //DialogInterface.onClickListener()
                )
                .setNegativeButton(
                        R.string.txtDialogoNegativo,
                        (dialog, which) -> dismiss() //DialogInterface.onClickListener()
                );
        return builder.create();
    }
}
