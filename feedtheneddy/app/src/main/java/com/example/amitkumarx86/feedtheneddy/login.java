package com.example.amitkumarx86.feedtheneddy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Scanner;

public class login extends AppCompatActivity {
    private Boolean exit = false;

    private static EditText username;
    private static EditText passwd;

    private static final String REGISTER_URL = "http://192.168.56.1/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void signIn(View view){


        username=(EditText)findViewById(R.id.email);
        passwd=(EditText)findViewById(R.id.password);
        String email = username.getText().toString();
        String password = passwd.getText().toString();
        if(!email.equals("") && !password.equals("")){
            doSignIn(email,password);
        }
        else{
            Toast.makeText(login.this,"Entries not filled..",
                    Toast.LENGTH_SHORT).show();

        }


    }
    public void signUp(View view){
        Intent rgs_page = new Intent(login.this,usr_registration.class);
        rgs_page.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        rgs_page.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        rgs_page.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(rgs_page);
    }


    public void doSignIn(String email, String password){

        class SignInUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DbActivity signin = new DbActivity();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(login.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Scanner sc = new Scanner(s.trim());

                if(sc.hasNext()){
                    if(sc.nextInt() > 0){
                        Intent launchNextActivity = new Intent(login.this,user.class);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(launchNextActivity);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("email",params[0]);
                data.put("password",params[1]);

                String result = signin.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        SignInUser su = new SignInUser();
        su.execute(email,password);

    }
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}

