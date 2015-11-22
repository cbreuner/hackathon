package edu.csumb.partyon.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.utils.CustomDialogBuilder;

/**
 * Created by Tobias on 21.11.2015.
 */
public class LocateFriendDialog extends DialogFragment {

    public static final String TAG = "LocateFriendDialog";

    private String friendId;

    public LocateFriendDialog(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
            friendId = args.getString(Constants.FRIEND_ID);

        handleGPS(null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CustomDialogBuilder builder = new CustomDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_locatefriend, null);

        builder.setCustomView(view)
                .setTitle(getResources().getString(R.string.locatefriend_title))
                .setIcon(R.drawable.ic_near_me_white_24dp)
                .setNeutralButton(getResources().getString(R.string.locatefriend_close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    private void handleGPS(Location target){
        Location currentLoc = AppState.getInstance().lastLocation;
        float azimuth = AppState.getInstance().lastAzimut;
        if(currentLoc == null || azimuth == -1){
            //TODO: Show error
            return;
        }
        azimuth = (float) Math.toDegrees(azimuth);
        GeomagneticField geoField = new GeomagneticField(
                (float) currentLoc.getLatitude(),
                (float) currentLoc.getLongitude(),
                (float) currentLoc.getAltitude(),
                System.currentTimeMillis());
        azimuth += geoField.getDeclination();
        float bearing = currentLoc.bearingTo(target);
        float direction = azimuth - bearing;
        float distance = currentLoc.distanceTo(target);
    }
}
