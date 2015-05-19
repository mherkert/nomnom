package com.mherkert.nomnom.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.mherkert.nomnom.R;

public class RecipeAddDialogFragment extends DialogFragment {

    public interface RecipeAddDialogFragmentCallbacks {
        void onDialogOpenCameraClick();
        void onDialogOpenGalleryClick();
    }
    // Use this instance of the interface to deliver action events
    RecipeAddDialogFragmentCallbacks mCallbacks;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallbacks = (RecipeAddDialogFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement RecipeAddDialogFragmentCallbacks");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_add_recipe)
                .setItems(R.array.dialog_add_recipe_choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mCallbacks.onDialogOpenCameraClick();
                                break;
                            case 1:
                                mCallbacks.onDialogOpenGalleryClick();
                                break;
                            default:break;
                        }
                    }
                });
        return builder.create();
    }
}