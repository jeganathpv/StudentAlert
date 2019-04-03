package com.jaddu.studentalert;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class IndividualActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button buttonClick;
    Spinner spin;
    EditText subject,body;
    String studName;
    String[] mailList,nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        buttonClick=findViewById(R.id.button_click);
        spin=findViewById(R.id.spin);
        subject=findViewById(R.id.subjectName);
        body=findViewById(R.id.bodyText);

        nameList= new String[]{"Ajith","Arun","Ashwin Kumar","Ashok","Dinesh A","Eswar","Fazil","Giri","Jegan","Kavin"};
        mailList= new String[]{"rohitajith.14898@gmail.com", "arunprasath7664@gmail.com","ashwinkumars076@gmail.com","g.ashokkumar1999@hotmail.com","dineshap07@gmail.com","eswaraswin007@gmail.com","amzahmed430@gmail.com","giridharsh98@gmail.com","jeganathpv@gmail.com","sskavin77@gmail.com"};

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studName=parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.encrypt:{
                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.decrypt:{

                return true;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.about){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name))
                    .setMessage(getString(R.string.about_text))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(
                            "https://api.whatsapp.com/send?phone=+919626997713"
                    )));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(IndividualActivity.this);
        builder.setMessage("Are you sure want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @SuppressLint("IntentReset")
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO={"test@test.com"};
        for(int i=0;i<nameList.length;i++){
            if(studName.equals(nameList[i])){
                TO[0]=mailList[i];
            }
        }

        String subj=subject.getText().toString();
        String bodyText=body.getText().toString();
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(IndividualActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
