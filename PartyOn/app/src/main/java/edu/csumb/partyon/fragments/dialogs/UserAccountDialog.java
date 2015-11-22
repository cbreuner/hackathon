package edu.csumb.partyon.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.Profile;

import edu.csumb.partyon.R;
import edu.csumb.partyon.utils.CustomDialogBuilder;

/**
 * Created by Tobias on 21.11.2015.
 * TODO: Create UI etc
 */
public class UserAccountDialog extends DialogFragment {

    public static final String TAG = "UserAccountDialog";

    public UserAccountDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CustomDialogBuilder builder = new CustomDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_useraccount, null);

        if(Profile.getCurrentProfile() != null)
            ((TextView)view.findViewById(R.id.user_name)).setText(Profile.getCurrentProfile().getName());

        builder.setCustomView(view)
                .setTitle(getResources().getString(R.string.useraccount_title))
                .setIcon(R.drawable.ic_account_circle_white_24dp)
                .setNeutralButton(getResources().getString(R.string.useraccount_close_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
