package edu.csumb.partyon.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import edu.csumb.partyon.R;
import edu.csumb.partyon.utils.CustomDialogBuilder;

/**
 * Created by Tobias on 21.11.2015.
 * TODO: Create UI etc
 */
public class SettingsDialog extends DialogFragment {

    public static final String TAG = "SettingsDialog";

    public SettingsDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CustomDialogBuilder builder = new CustomDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, null);

        builder.setCustomView(view)
                .setTitle(getResources().getString(R.string.useraccount_title))
                .setIcon(R.drawable.ic_settings_white_24dp)
                .setNeutralButton(getResources().getString(R.string.useraccount_close_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
