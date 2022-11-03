package es.upm.miw.bantumi.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import es.upm.miw.bantumi.Activities.RankingActivity;
import es.upm.miw.bantumi.R;

public class DeleteAllDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final RankingActivity activity = (RankingActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.txtDialogoDeleteAllTitulo)
                .setMessage(R.string.txtDialogoDeleteAllPregunta)
                .setPositiveButton(R.string.txtDialogoAfirmativo,
                        (dialog, which) -> activity.deleteAll()
                )
                .setNegativeButton(R.string.txtDialogoNegativo,
                        (dialog, which) -> dismiss()
                );
        return builder.create();
    }
}
