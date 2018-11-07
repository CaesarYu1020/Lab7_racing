package com.example.caesaryu.lab7;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected Button btn;
    protected SeekBar rabbit_bar, tortoise_bar;
    int rab_count = 0, tor_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        rabbit_bar = findViewById(R.id.rabbar);
        tortoise_bar = findViewById(R.id.torbar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"START", Toast.LENGTH_LONG).show();

                runThread();
                runAsyncTask();

            }
        });
    }

    private void runThread() {
        rab_count = 0;
        new Thread() {
            public void run() {
                do {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rab_count += (int) (Math.random() * 3);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } while (rab_count <= 100);
            }

        }.start();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rabbit_bar.setProgress(rab_count);
                    break;
            }
            if(rab_count>=100)
                if(tor_count<100)
                    Toast.makeText(MainActivity.this,"兔子完成",Toast.LENGTH_LONG).show();

        }
    };

    private void runAsyncTask() {
new AsyncTask<Void,Integer,Boolean>(){
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        tor_count=0;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
       do{
           try{
               Thread.sleep(1000);
           }catch (InterruptedException e){
               e.printStackTrace();
           }
           tor_count+=(int) (Math.random() * 3);
           publishProgress(tor_count);
       }while (tor_count<=100);

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tortoise_bar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(rab_count<100)
            Toast.makeText(MainActivity.this,"烏龜獲勝",Toast.LENGTH_LONG).show();

    }
}.execute();

    }

}
