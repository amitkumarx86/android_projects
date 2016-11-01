package com.example.amitkumarx86.myapplication;

import android.support.v7.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    dbHandler dbHandler1;
    EditText t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (EditText) findViewById(R.id.t);
        dbHandler1 = new dbHandler(this,null,null,1);
    }

    public void addTopic(View view){
        String data = t1.getText().toString();
        dbHandler1.addVideo(data,data + "1", data + "2");
        String result = dbHandler1.tableSelect(data);
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }
}
