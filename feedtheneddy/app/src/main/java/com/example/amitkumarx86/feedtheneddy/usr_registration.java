package com.example.amitkumarx86.feedtheneddy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class usr_registration extends AppCompatActivity{


    private Boolean exit = false;


    private static TextView email;
    private static TextView pass;
    private static TextView cnf_pass;
    private static TextView address;
    private static TextView phone1;
    private static TextView phone2;

    private static final String REGISTER_URL = "http://192.168.56.1/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usr_registration);

    }

    public void registration(View v) {

        email = (EditText)findViewById(R.id.email);
        pass  = (EditText)findViewById(R.id.password);
        cnf_pass = (EditText)findViewById(R.id.cnf_password);
        address = (EditText)findViewById(R.id.address);
        phone1 = (EditText)findViewById(R.id.phone1);
        phone2 = (EditText)findViewById(R.id.phone2);

        if(!pass.getText().toString().equals( cnf_pass.getText().toString())){
            Toast.makeText(usr_registration.this,"Password don't match",
                    Toast.LENGTH_SHORT).show();

        }
        else{
            if (!(email.getText().toString() == "" && pass.getText().toString() == "" && cnf_pass.getText().toString() == ""
                    && address.getText().toString() == ""  && phone1.getText().toString() == ""  ) && (phone1.getText().length() == 10)){

                Toast.makeText(usr_registration.this,"Signing Up..",
                        Toast.LENGTH_SHORT).show();

                doSignUp(email.getText().toString(),pass.getText().toString(),
                        address.getText().toString(),phone1.getText().toString(),phone2.getText().toString());



            }
            else {
                Toast.makeText(usr_registration.this,"Few Entries are NOT correct",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void doSignUp(String email, String password, String address, String phone1, String phone2){

        class SignInUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DbActivity signin = new DbActivity();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(usr_registration.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(),"Thank You for Joining :)",Toast.LENGTH_LONG).show();

                    Intent launchNextActivity = new Intent(usr_registration.this,user.class);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(launchNextActivity);
                }
                else
                    Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("email",params[0]);
                data.put("password",params[1]);
                data.put("address",params[2]);
                data.put("phone1",params[3]);
                data.put("phone2",params[4]);

                String result = signin.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        SignInUser su = new SignInUser();
        su.execute(email,password,address,phone1,phone2);

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

