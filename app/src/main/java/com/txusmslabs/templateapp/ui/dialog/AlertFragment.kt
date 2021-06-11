package com.txusmslabs.templateapp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.txusmslabs.templateapp.R

class AlertFragment: DialogFragment() {

    private val args: AlertFragmentArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            builder.setMessage(args.message)
                .setPositiveButton(R.string.dialog_ok_button
                ) { _, _ ->
                    // TODO
                }
                .setNegativeButton(R.string.dialog_cancel_button
                ) { _, _ ->
                    // TODO: Cancel
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}