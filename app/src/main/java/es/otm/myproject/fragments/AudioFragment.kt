package es.otm.myproject.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.otm.myproject.R
import es.otm.myproject.databinding.FragmentAudioBinding

class AudioFragment : Fragment() {

    private lateinit var binding: FragmentAudioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioBinding.inflate(inflater, container, false)

        var audio = MediaPlayer.create(requireContext(), R.raw.audioproyecto)

        binding.buttonStart.setOnClickListener{
            audio.start()
            binding.buttonStart.setText("START")
        }

        binding.buttonPause.setOnClickListener{
            audio.pause()
            binding.buttonStart.setText("CONTINUE")
        }

        binding.buttonStop.setOnClickListener{
            audio.stop()
            audio.release()
            audio = MediaPlayer.create(requireContext(), R.raw.audioproyecto)
            vibrateDevice()
        }

        return binding.root
    }

    fun vibrateDevice(){
        var vibrator: Vibrator
        if (Build.VERSION.SDK_INT >= 31){
            val vibratorManager = activity?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        }
        else{
            @Suppress("DEPRECATION")
            vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val mVibratePattern = longArrayOf(0, 400, 200, 400)
        if (Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createWaveform(mVibratePattern, -1))
        }
        else{
            @Suppress("DEPRECATION")
            vibrator.vibrate(mVibratePattern, -1)
        }
    }

}