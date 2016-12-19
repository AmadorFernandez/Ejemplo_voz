package za.co.zebrav.voice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.fhooe.mcm.smc.math.mfcc.FeatureVector;
import at.fhooe.mcm.smc.math.vq.Codebook;
import at.fhooe.mcm.smc.math.vq.KMeans;

public class MainActivity extends AppCompatActivity {

    VoiceRecorder voiceRecorder;

    protected VoiceAuthenticator voiceAuthenticator;
    protected ProgressDialog soundLevelDialog;
    protected ProgressDialog processingDialog;
    protected Activity activity;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activity = this;
        this.soundLevelDialog = new ProgressDialog(activity, ProgressDialog.STYLE_HORIZONTAL);
        this.soundLevelDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        soundLevelDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {





            }
        });
        this.soundLevelDialog.setTitle("Listening...");
        this.processingDialog = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        this.processingDialog.setMessage("Processing");
        this.processingDialog.setCancelable(false);
        soundLevelDialog.setCancelable(true);
        voiceRecorder = new VoiceRecorder(soundLevelDialog);
        this.voiceAuthenticator = new VoiceAuthenticator(soundLevelDialog);
    }

    public void btTrain(View view) {


    }

    public void btRecognise(View view) {

    }

    public void btPlay(View view) {

        voiceAuthenticator.dialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                /*Aqui empieza a grabar pero no se activa hasta que empiezas tu a hablar
                * y termina cuando dejas de hablar mas o menos durante un segundo. Fijate
                * en log como va escribiendo cuando hablas.
                * */
               voiceAuthenticator.startRecording();
                KMeans m = voiceAuthenticator.doClustering(voiceAuthenticator.getCurrentFeatureVector());
                Codebook codebook = voiceAuthenticator.createCodebook(m);
                voiceAuthenticator.insertFeature(codebook);
                float l = voiceAuthenticator.identifySpeaker(voiceAuthenticator.getCurrentFeatureVector());


            }
        });




    }
}
