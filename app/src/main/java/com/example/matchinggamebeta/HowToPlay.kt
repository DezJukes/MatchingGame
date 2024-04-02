package com.example.matchinggamebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [HowToPlay.newInstance] factory method to
 * create an instance of this fragment.
 */
class HowToPlay : Fragment() {
    private lateinit var homeButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_how_to_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeButton = view.findViewById(R.id.btnHowToHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_howToPlay_to_FirstFragment)
        }
    }

}