package sanjarbek.uz.patenttest.ui.custom_widgets

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import sanjarbek.uz.patenttest.R


@SuppressLint("DefaultLocale")
class AudioPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialCardView(context, attrs) {

    private val btnPlayPause: ImageView
    private val tvCurrentTime: TextView
    private val seekBar: SeekBar
    private val imgVolume: ImageView

    private var mediaPlayer: MediaPlayer? = null
    private var isMuted = false
    private val handler = Handler(Looper.getMainLooper())
    private var updateSeekRunnable: Runnable? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_audio_player_view, this, true)

        // Viewlar bilan bog‘lanamiz
        btnPlayPause = findViewById(R.id.btnPlayPause)
        tvCurrentTime = findViewById(R.id.tvCurrentTime)
        seekBar = findViewById(R.id.seekBar)
        imgVolume = findViewById(R.id.imgVolume)

        // Tugmalar listenerlari
        btnPlayPause.setOnClickListener { togglePlayPause() }
        imgVolume.setOnClickListener { toggleVolume() }

        // Seekbar holatini kuzatish
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    tvCurrentTime.text = formatTime(progress, mediaPlayer?.duration ?: 0)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        // CardView default styling
        radius = 8f
        cardElevation = 0f
        strokeWidth = 0
    }

    fun setAudioUrl(audioUrl: String) {
        releasePlayer()

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                seekBar.max = duration
                tvCurrentTime.text = formatTime(0, duration)
                startUpdatingSeekbar()
            }
            setOnCompletionListener {
                btnPlayPause.setImageResource(R.drawable.ic_media_play)
                seekBar.progress = 0
            }
        }
    }

    fun setAudioId(audioId: Int) {
        releasePlayer()

        mediaPlayer = MediaPlayer.create(context, audioId)
        mediaPlayer?.let {
            seekBar.max = it.duration
            tvCurrentTime.text = formatTime(0, it.duration)
            it.setOnCompletionListener {
                btnPlayPause.setImageResource(R.drawable.ic_media_play)
                seekBar.progress = 0
            }
            startUpdatingSeekbar()
        }
    }

    private fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                btnPlayPause.setImageResource(R.drawable.ic_media_play)
            } else {
                it.start()
                btnPlayPause.setImageResource(R.drawable.ic_media_pause)
            }
        }
    }

    private fun toggleVolume() {
        mediaPlayer?.let {
            isMuted = !isMuted
            val volume = if (isMuted) 0f else 1f
            it.setVolume(volume, volume)
            imgVolume.setImageResource(
                if (isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up
            )
        }
    }

    private fun startUpdatingSeekbar() {
        updateSeekRunnable = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    val pos = it.currentPosition
                    seekBar.progress = pos
                    tvCurrentTime.text = formatTime(pos, it.duration)
                    handler.postDelayed(this, 500)
                }
            }
        }
        handler.post(updateSeekRunnable!!)
    }

    private fun formatTime(currentMs: Int, totalMs: Int): String {
        fun msToMinSec(ms: Int): String {
            val sec = ms / 1000
            val min = sec / 60
            return String.format("%d:%02d", min, sec % 60)
        }

        return "${msToMinSec(currentMs)} / ${msToMinSec(totalMs)}"
    }

    private fun releasePlayer() {
        updateSeekRunnable?.let { handler.removeCallbacks(it) }
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releasePlayer()
    }
}
