package es.upm.miw.bantumi.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import es.upm.miw.bantumi.Activities.RankingActivity;
import es.upm.miw.bantumi.R;

public class FilterByPlayerDialog extends AppCompatDialogFragment {
    String player;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final RankingActivity activity = (RankingActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.txtBtnFilter)
                .setSingleChoiceItems(
                        activity.getPlayerArray(),
                        -1,
                        (dialog, which) -> {
                            ListView lw = ((AlertDialog) dialog).getListView();
                            if (lw.getCheckedItemCount() > 0) {
                                player = lw.getAdapter().getItem(lw.getCheckedItemPosition()).toString();
                            }
                        }
                )
                .setPositiveButton(
                        R.string.txtDialogoAccept,
                        (dialog, which) -> activity.filterByPlayer(player)
                )
                .setNegativeButton(
                        R.string.txtDialogoCancel,
                        (dialog, which) -> dismiss()
                );
        return builder.create();
    }
}
