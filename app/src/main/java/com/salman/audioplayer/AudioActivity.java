package com.salman.audioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener {

    TextView titleTv, currentTimeTv, totalTimeTv, setTime;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, btnotification, mainImage, btnStoppp, imgLoop, imgList;
    //ArrayList<AudioModel> songsList;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x = 0;
    boolean isEnabled = true;
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    int i;
    TextView textAlarmPrompt, txtSpeed;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    TextView textViewTime;
    CounterClass timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        //titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        imgLoop = findViewById(R.id.imgLoop);

        btnotification = findViewById(R.id.btnotification);
        mainImage = findViewById(R.id.mainImage);
        btnStoppp = findViewById(R.id.btnStop);
        setTime = findViewById(R.id.setTime);
        textViewTime = findViewById(R.id.textViewTime);
        txtSpeed = findViewById(R.id.txtSpeed);
        imgList = findViewById(R.id.list);

        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);

        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        imgList.setOnClickListener(this);
        setTime.setOnClickListener(this);
        btnStoppp.setOnClickListener(this);
        txtSpeed.setOnClickListener(this);
        imgLoop.setOnClickListener(this);

        i = getIntent().getIntExtra("status", 0);


        ////////

        getMusic(i);
        setResourcesWithMusic();
        playMusic();

        AudioActivity.this.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                if (mediaPlayer != null) {

                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));

                    if (mediaPlayer.isPlaying()) {

                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                    } else {
//                        nPanel.notificationCancel();
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }

                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void getMusic(int i) {
        if (i > 0) {
            String fileName = null;

            if (i == 1) {
                fileName = "srbbq";
                previousBtn.setAlpha(0.4f);
            } else if (i == 2) {
                fileName = "al_fatiha";
                previousBtn.setAlpha(1f);
                nextBtn.setAlpha(1f);
            } else if (i == 3) {
                fileName = "azan_al_makkah";
                previousBtn.setAlpha(1f);
                nextBtn.setAlpha(1f);
            } else if (i == 4) {
                fileName = "dua_e_qanoot";
                previousBtn.setAlpha(1f);
                nextBtn.setAlpha(1f);
            } else if (i == 5) {
                fileName = "arruqyatusharia";
                nextBtn.setAlpha(0.4f);
            }


            Uri uri = Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + fileName);


            //
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)

            } catch (IOException e) {
                e.printStackTrace();
            }
            setResourcesWithMusic();
//            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
        }
    }

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            // TODO Auto-generated method stub
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };

    void setResourcesWithMusic() {
        totalTimeTv.setText(convertToMMSS(String.valueOf(mediaPlayer.getDuration())));
        pausePlay.setOnClickListener(v -> pausePlay());
        playMusic();

    }

    private void playMusic() {
        mediaPlayer.start();
        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void pausePlay() {
        try {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            else
                mediaPlayer.start();

        } catch (NullPointerException nullPointerException) {
        }
    }


    public static String convertToMMSS(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnotification:
//                showDialog();
                break;

            case R.id.list:
                showSpeedDialogue2();
                break;

            case R.id.previous:
                if (i > 0) {
                    i--;
                    getMusic(i);
                }

                break;

            case R.id.next:
                if (i < 5) {
                    i++;
                    getMusic(i);
                 }
                break;

            case R.id.imgLoop:
                if (isEnabled) {
                    imgLoop.setAlpha(1f);
                    mediaPlayer.setLooping(true);
                    Toast.makeText(this, "Looping Enabled", Toast.LENGTH_SHORT).show();
                    isEnabled = false;
                } else {
                    imgLoop.setAlpha(0.4f);
                    mediaPlayer.setLooping(false);
                    Toast.makeText(this, "Looping Disabled", Toast.LENGTH_SHORT).show();
                    isEnabled = true;
                }
                break;
            case R.id.setTime:
                openTimePickerDialog(false);
                break;
            case R.id.btnStop:
                timer.cancel();
                btnStoppp.setVisibility(View.GONE);
                textViewTime.setVisibility(View.GONE);
                break;
            case R.id.txtSpeed:
                showSpeedDialogue();
                break;
        }
    }

//    private void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        builder.setTitle("Set As");
//
//        String[] animals = {"Ringtone", "Alarm", "Notification"};
//        int checkedItem = 1; // cow
//        builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void showSpeedDialogue() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AudioActivity.this);
        builderSingle.setTitle("Select Speed");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AudioActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("1x");
        arrayAdapter.add("1.25x");
        arrayAdapter.add("1.5x");
        arrayAdapter.add("1.75x");
        arrayAdapter.add("2x");
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(AudioActivity.this);
//                Toast.makeText(AudioActivity.this, strName, Toast.LENGTH_SHORT).show();
                float speed = 0;

                switch (strName) {
                    case "1x":
                        speed = 1f;
                        break;
                    case "1.25x":
                        speed = 1.25f;
                        break;
                    case "1.5x":
                        speed = 1.5f;

                        break;
                    case "1.75x":
                        speed = 1.75f;

                        break;
                    case "2x":
                        speed = 2f;
                        break;
                }

                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                txtSpeed.setText(strName);
            }
        });
        builderSingle.show();
    }

    private void showSpeedDialogue2() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AudioActivity.this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AudioActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Al Baqra");
        arrayAdapter.add("Al Fatiha");
        arrayAdapter.add("Azan Al Makkah");
        arrayAdapter.add("Dua e Qanoot");
        arrayAdapter.add("Ruqqaiat ul Sharia");

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(AudioActivity.this);
                i = 0;
                i = which + 1;
                getMusic(i);
            }
        });
        builderSingle.show();
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                AudioActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);

        timePickerDialog.setTitle("Set Sleep Time");
        timePickerDialog.show();
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timer = new CounterClass(((long) minute * 1000) + ((long) hourOfDay * 1000), 1000);
            long millis = ((long) minute * 1000) + ((long) hourOfDay * 1000);
            String hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            timer.start();


        }
    };


    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
            textViewTime.setVisibility(View.VISIBLE);
            btnStoppp.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinish() {
            timer.cancel();
            if (mediaPlayer != null) {
                seekBar.setProgress(0);
                textViewTime.setVisibility(View.GONE);
                btnStoppp.setVisibility(View.GONE);
                pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                mediaPlayer.pause();

            }
        }
    }


}
