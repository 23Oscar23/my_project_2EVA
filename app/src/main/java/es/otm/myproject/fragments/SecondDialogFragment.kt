package es.otm.myproject.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import es.otm.myproject.R

class SecondDialogFragment : DialogFragment() {

    private lateinit var mListener: SecondDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("EXIT")
                .setMessage("Do you want to exit?")
                .setPositiveButton("YES"){ dialog, id ->
                    mListener.onYesClick()
                }
                .setNegativeButton("NO"){ dialog, id ->

                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    interface SecondDialogListener{
        fun onYesClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SecondDialogListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }
}