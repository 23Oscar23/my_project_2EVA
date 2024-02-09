package es.otm.myproject.classes

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.preference.Preference
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import es.otm.myproject.SettingsActivity

class ColorClass(context: Context, attrs: AttributeSet): Preference(context, attrs){
    private var selectedColor: Int = DEFAULT_COLOR

    init {
        setOnPreferenceClickListener {
            showColorPickerDialog()
            true
        }
    }

    private fun showColorPickerDialog(){
        val builder = ColorPickerDialog.Builder(context)
            .setTitle("Pick a Color")
            .setPreferenceName(SettingsActivity.COLOR)
            .setPositiveButton("Confirm", ColorEnvelopeListener { envelope, _ ->
                selectedColor = envelope.color
                persistInt(selectedColor)
                notifyChanged()
            })
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        builder.colorPickerView.setInitialColor(selectedColor)
        builder.show()
    }

    override fun getSummary(): CharSequence? {
        return String.format("#%06X", 0xFFFFFF and selectedColor)
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)
        selectedColor = getPersistedInt(DEFAULT_COLOR)
    }

    companion object{
        val DEFAULT_COLOR = Color.parseColor("#FCA400")
    }
}
