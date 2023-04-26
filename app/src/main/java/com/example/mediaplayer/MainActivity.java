package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.renaissance);
        iniciarSeekBar();
    }

    private void iniciarSeekBar() {
        seekVolume = findViewById(R.id.seekVolume);

        //Configurar audio manager parasaber niveis de volume
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Recuperar valores de volume do telefone
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //Configurar valores máximos para a SeekBar
        seekVolume.setMax(volumeMaximo);
        //Configura o progresso da SeekBar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Configura volume da musica baseado na barra do SeekBar
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void executarMusica(View view) {
        if(mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pausarMusica(View view) {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void pararMusica(View view) {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.renaissance);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            //Libera a memória
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /*  //Para pausar a música ao sair do App
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }*/

}