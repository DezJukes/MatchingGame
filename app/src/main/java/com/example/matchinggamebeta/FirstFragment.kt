package com.example.matchinggamebeta

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.matchinggamebeta.databinding.FragmentFirstBinding
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var backgroundMusicPlayer: MediaPlayer
    private lateinit var buttonSoundClick: MediaPlayer

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundMusicPlayer = MediaPlayer.create(requireContext(), R.raw.bgm_menu1)
        backgroundMusicPlayer.isLooping = true // Loop the music
        backgroundMusicPlayer.start()

        buttonSoundClick = MediaPlayer.create(requireContext(), R.raw.press)

        binding.buttonFirst.setOnClickListener {
            buttonSoundClick.start()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonSecond.setOnClickListener {
            buttonSoundClick.start()
            exitProcess(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        backgroundMusicPlayer.release()
        buttonSoundClick.release()
    }
}