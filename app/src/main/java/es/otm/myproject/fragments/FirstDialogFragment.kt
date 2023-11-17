package es.otm.myproject.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import es.otm.myproject.R


class FirstDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mListener: FirstDialogListener
    private lateinit var petSelected: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.my_pet_dialog, null)

            petSelected = view.findViewById(R.id.petSelected)

            val myAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.mascotasDisponibles, android.R.layout.simple_spinner_item)
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            petSelected.adapter = myAdapter
            petSelected.onItemSelectedListener = this

            builder
                .setView(view)
                .setPositiveButton("CREATE"){ dialog, id ->
                    mListener.onDialogClick()
                }
                .setNegativeButton("CANCEL"){ dialog, id ->
                    mListener.onCancelClick()
                }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface FirstDialogListener{
        fun onDialogClick()
        fun onCancelClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FirstDialogListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FirstDialogListener")
        }
    }

    override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedPet = parentView?.getItemAtPosition(position) as String
    }

    override fun onNothingSelected(parentView: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}