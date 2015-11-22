package edu.csumb.partyon.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.csumb.partyon.R;

/**
 * Created by Tobias on 12.02.14.
 */
public class CustomDialogBuilder extends AlertDialog.Builder{

    private View dialogLayout;
    private TextView dialogTitle;
    private ImageView dialogIcon;
    private ImageButton dialogAction;
    private TextView dialogMessage;
    private LinearLayout dialogMessageContainer;

    public CustomDialogBuilder(Context context) {
        super(context);

        dialogLayout = View.inflate(context, R.layout.custom_dialog_layout, null);
        setView(dialogLayout);

        dialogTitle = (TextView) dialogLayout.findViewById(R.id.customd_title);
        dialogTitle.setTextColor(context.getResources().getColor(android.R.color.white));

        dialogMessageContainer = (LinearLayout) dialogLayout.findViewById(R.id.customd_content_layout);
        dialogMessage = (TextView) dialogLayout.findViewById(R.id.customd_message);

        dialogIcon = (ImageView) dialogLayout.findViewById(R.id.customd_icon);
        dialogAction = (ImageButton) dialogLayout.findViewById(R.id.customd_action);
    }

    @Override
    public CustomDialogBuilder setTitle(CharSequence text) {
        dialogTitle.setText(text);
        return this;
    }

    @Override
    public CustomDialogBuilder setTitle(int resId){
        dialogTitle.setText(resId);
        return this;
    }

    @Override
    public CustomDialogBuilder setMessage(int textResId) {
        dialogMessageContainer.setVisibility(View.VISIBLE);
        dialogMessage.setText(textResId);
        return this;
    }

    @Override
    public CustomDialogBuilder setIcon(int drawableResId) {
        dialogIcon.setImageResource(drawableResId);
        return this;
    }

    public CustomDialogBuilder setAction(int drawableResId, View.OnClickListener listener){
        dialogAction.setVisibility(View.VISIBLE);
        dialogAction.setImageResource(drawableResId);
        dialogAction.setOnClickListener(listener);
        return this;
    }

    public CustomDialogBuilder setActionState(boolean state){
        dialogAction.setEnabled(state);
        if(!state)
            dialogAction.setColorFilter(Color.parseColor("#66000000"));
        return this;
    }

    public CustomDialogBuilder setCustomView(View view) {
        ((FrameLayout) dialogLayout.findViewById(R.id.customd_extra_view)).addView(view);
        return this;
    }
}