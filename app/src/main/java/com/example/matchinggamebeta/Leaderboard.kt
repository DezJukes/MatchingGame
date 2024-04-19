package com.example.matchinggamebeta

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Use the [Leaderboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Leaderboard : Fragment() {
    private lateinit var homeButton:ImageButton
    private lateinit var switch:Switch
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var Table:TableLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeButton = view.findViewById(R.id.btnSettingsToHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_leaderboard_to_FirstFragment)
        }

        switch = view.findViewById(R.id.switch1)

        sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        switch.isChecked = sharedPreferences.getBoolean("switch_state", false)

        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("switch_state", isChecked).apply()
            if (isChecked) {
                // Turn on the background music
                (activity as MainActivity).startBackgroundMusic()
            } else {
                // Turn off the background music
                (activity as MainActivity).stopBackgroundMusic()
            }
        }

        val leaderboard = listOf(
            LeaderboardEntry(1, "Vinzel", 95, "NM+"),
            LeaderboardEntry(2, "Bryan", 90),
            LeaderboardEntry(3, "Louise", 85),
            LeaderboardEntry(4, "Raven", 80),
            LeaderboardEntry(5, "Lord", 75),
            LeaderboardEntry(6, "Ric", 70),
            LeaderboardEntry(7, "Geleen", 65),
            LeaderboardEntry(8, "Alice", 60),
            LeaderboardEntry(9, "Miracle", 55),
            LeaderboardEntry(10, "Ingaran", 50)
        )

        val tableLayout = view.findViewById<TableLayout>(R.id.tablelayout)

        for (entry in leaderboard) {
            createTableRow(tableLayout, entry)
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("switch_state", false)) {
            (activity as MainActivity).startBackgroundMusic()
        }
    }

    private fun createTableRow(tableLayout: TableLayout, entry: LeaderboardEntry) {
        val tableRow = TableRow(requireContext())

        val idTextView = TextView(requireContext())
        idTextView.text = entry.id.toString().padStart(2)
        idTextView.gravity = Gravity.CENTER
        idTextView.setPadding(0, 0, 16, 0)
        tableRow.addView(idTextView)

        val playerTextView = TextView(requireContext())
        playerTextView.text = entry.player.padEnd(10)
        playerTextView.gravity = Gravity.CENTER
        playerTextView.setPadding(0, 0, 16, 0) // Adjust padding as needed
        tableRow.addView(playerTextView)

        val highscoreTextView = TextView(requireContext())
        highscoreTextView.text = entry.highscore.toString()
        highscoreTextView.gravity = Gravity.CENTER
        highscoreTextView.setPadding(0, 0, 16, 0) // Adjust padding as needed
        tableRow.addView(highscoreTextView)

        tableLayout.addView(tableRow)
    }

}

data class LeaderboardEntry(
    val id: Int,
    val player: String,
    val highscore: Int,
    val specifier: String? = null
)
