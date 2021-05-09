package com.devJamesP.audiorecoderactivity

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val soundVisualizerView: SoundVisualizerView by lazy {
        findViewById(R.id.soundVisualizerView)
    }

    private val recordTimeTextView: CountUpView by lazy {
        findViewById(R.id.recordTimeTextView)
    }

    private val resetButton: Button by lazy {
        findViewById(R.id.resetButton)
    }

    private val recordButton: RecordButton by lazy {
        findViewById(R.id.recordButton)
    }
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absoluteFile}/recording.3gp"
    }
    private var player: MediaPlayer? = null
    private var recoder: MediaRecorder? = null
    private var state = State.BEFORE_RECORDING
    set(value) {
        field = value
        resetButton.isEnabled = value == State.AFTER_RECORDING || value == State.ON_PLAYING
        recordButton.updateIconWithState(value)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission()
        initViews()
        bindViews()
        initVariables()
    }


    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            finish()
        }
    }

    private fun initViews() {
        recordButton.updateIconWithState(state)
    }

    private fun bindViews() {
        soundVisualizerView.onRequestCurrentAmplitude = {
            recoder?.maxAmplitude ?: 0
        }

        resetButton.setOnClickListener {
            stopPlaying()
            soundVisualizerView.clear()
            recordTimeTextView.clearCountTime()
            state = State.BEFORE_RECORDING
        }


        recordButton.setOnClickListener {
            when(state) {
                State.BEFORE_RECORDING -> {
                    startRecording()

                }
                State.ON_RECORDING -> {
                    stopRecording()
                }

                State.AFTER_RECORDING -> {
                    startPlaying()
                }

                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }

    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    private fun startRecording() {
        recoder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        }
        recoder?.start()
        soundVisualizerView.startVisualizing(isReplaying = false)
        recordTimeTextView.startCountUp()
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recoder?.run {
            stop()
            release()
        }
        recoder = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING

    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
        }
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }
        player?.start()
        soundVisualizerView.startVisualizing(isReplaying = true)
        recordTimeTextView.startCountUp()
        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.run {
            release()
        }
        player = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}