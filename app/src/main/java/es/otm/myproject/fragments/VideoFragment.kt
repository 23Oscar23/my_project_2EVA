package es.otm.myproject.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import es.otm.myproject.R
import es.otm.myproject.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        binding.buttonLoad.setOnClickListener{
            val videoElegido = binding.nombreVideo.text.toString()
            if (!videoElegido.isEmpty()){
                prepareVideo(videoElegido)
                binding.videoView.start()
            }
        }

        return binding.root
    }

    private fun prepareVideo(nameVideo: String){
        val idVideo: Int = resources.getIdentifier(nameVideo,"raw", requireContext().packageName)
        binding.videoView.setVideoURI(
            Uri.parse("android.resource://" + requireContext().packageName + "/" + idVideo)
        )
        val controladorMedia = MediaController(requireContext())
        controladorMedia.setAnchorView(binding.videoView)
        controladorMedia.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controladorMedia)
    }
}