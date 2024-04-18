package com.example.matchinggamebeta

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.matchinggamebeta.databinding.ActivityMainBinding
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var backgroundMusicPlayer: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SwitchState", Context.MODE_PRIVATE)

        backgroundMusicPlayer = MediaPlayer.create(this, R.raw.bgm_menu1)
        backgroundMusicPlayer.isLooping = true // Loop the music
        if (sharedPreferences.getBoolean("switch_state", false)) {
            startBackgroundMusic()
        } else {
            stopBackgroundMusic()
        }
        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        //toolbar.setTitleTextColor(Color.WHITE)
        //setSupportActionBar(binding.toolbar)

        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onPause() {
        super.onPause()

    }
    fun startBackgroundMusic() {
        if (!backgroundMusicPlayer.isPlaying) {
            backgroundMusicPlayer.seekTo(0)
            backgroundMusicPlayer.start()
        }
    }

    fun stopBackgroundMusic() {
        if (backgroundMusicPlayer.isPlaying) {
            backgroundMusicPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusicPlayer.release()
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}