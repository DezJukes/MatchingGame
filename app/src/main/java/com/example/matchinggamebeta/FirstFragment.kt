package com.example.matchinggamebeta

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
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
    private var isBackgroundMusicPlaying = false
    private lateinit var settingsButton:ImageButton
    private lateinit var aboutUsButton:Button

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

        //backgroundMusicPlayer = MediaPlayer.create(requireContext(), R.raw.bgm_menu1)
        //backgroundMusicPlayer.isLooping = true // Loop the music

        buttonSoundClick = MediaPlayer.create(requireContext(), R.raw.press)

        binding.buttonFirst.setOnClickListener {
            buttonSoundClick.start()
            findNavController().navigate(R.id.action_FirstFragment_to_levelSelect)
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_howToPlay)
        }

        binding.buttonThird.setOnClickListener {
            buttonSoundClick.start()
            exitProcess(0)
        }

        settingsButton = view.findViewById(R.id.btnSettings)
        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_leaderboard)
        }

        aboutUsButton = view.findViewById(R.id.btnAboutUs)
        aboutUsButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_aboutUs)
        }
    }
    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("switch_state", false)) {
            (activity as MainActivity).startBackgroundMusic()
        }
    }

    override fun onPause() {
        super.onPause()

    }
}