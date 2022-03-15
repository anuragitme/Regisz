package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String tagz="MainActivity";

    private Button dpBtn,submitBtn;
    private RadioGroup rgGender;
    private CheckBox agreeCB;
    private EditText name,email,password,repassword;
    private Spinner countrySpnr;
    private ProgressBar myPbar;
    private TextView warnName,warnEmail,warnPass,warnRepass,warnCountry;
    private RelativeLayout parent;
    private static ArrayList<Candidates> candidateList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dpBtn=findViewById(R.id.profileBtn);
        submitBtn=findViewById(R.id.submitBtn);
        agreeCB=findViewById(R.id.agreeCB);
        rgGender=findViewById(R.id.rgGender);
        name=findViewById(R.id.editName);
        email=findViewById(R.id.editEmail);
        password=findViewById((R.id.editPassword));
        repassword=findViewById(R.id.editRePassword);
        countrySpnr=findViewById(R.id.countrySpinner);
        myPbar=findViewById(R.id.myPB);
        warnName=findViewById(R.id.warnNameTxt);
        warnEmail=findViewById(R.id.warnEmailTxt);
        warnPass=findViewById(R.id.warnPasswordTxt);
        warnRepass=findViewById(R.id.warnRePasswordTxt);
        warnCountry=findViewById(R.id.warnCountry);
        parent=findViewById(R.id.parent);

        dpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Your Profile pic is set", Toast.LENGTH_SHORT).show();
            }
        });

        Thread progressThread=new Thread(new Runnable() {
            @Override
            public void run() {
                    while(true){
                        int progressVal=0;
                        if(!name.getText().toString().equals("")){
                            progressVal+=15;
                        }
                        if(!email.getText().toString().equals("")){
                            progressVal+=15;
                        }
                        if(!password.getText().toString().equals("")){
                            progressVal+=20;
                        }
                        if(!repassword.getText().toString().equals("")){
                            progressVal+=15;
                        }
                        if(agreeCB.isChecked()){
                            progressVal+=20;
                        }
                        if(countrySpnr.getSelectedItem()!=null){
                            progressVal+=15;
                        }
                        myPbar.setProgress(progressVal);

                    }
            }
        });
        progressThread.start();

        agreeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "You have agreed to our T&C", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Unchecked T&C", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.maleRbtn:
                        Toast.makeText(MainActivity.this, "Male is selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.femaleRbtn:
                        Toast.makeText(MainActivity.this, "Female is selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.othersRbtn:
                        Toast.makeText(MainActivity.this, "Others is selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        ArrayList<String> countryList=new ArrayList<>();
        countryList.add("India");
        countryList.add("USA");
        countryList.add("Russia");
        countryList.add("England");
        countryList.add("Germany");
        countryList.add("Australia");

        ArrayAdapter<String> countryAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,countryList);
        countrySpnr.setAdapter(countryAdapter);
        countrySpnr.setSelection(0);

        countrySpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,countryList.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister();
            }
        });

    }

    private void initRegister() {
        Log.d(tagz,"initRegister:started");
        if(validateFields()){
            if(agreeCB.isChecked()){
                showSnackbar();

            }
            else{
                Toast.makeText(this, "Please agree to our T&C to continue", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void showSnackbar() {
        Log.d(tagz,"showSnackbar:started");
        warnName.setVisibility(View.GONE);
        warnEmail.setVisibility(View.GONE);
        warnPass.setVisibility(View.GONE);
        warnRepass.setVisibility(View.GONE);
        warnCountry.setVisibility(View.GONE);

        String cname=name.getText().toString();
        String cemail=email.getText().toString();
        String cpass=password.getText().toString();
        String ccountry=countrySpnr.getSelectedItem().toString();
        String cgender="Unknown";
        switch (rgGender.getCheckedRadioButtonId()){
            case R.id.maleRbtn:
                cgender="Male";
                break;
            case R.id.femaleRbtn:
                cgender="Female";
                break;
            case R.id.othersRbtn:
                cgender="Others";
                break;
            default:
                break;
        }
        candidateList.add(new Candidates(cname,cemail,cpass,cgender,ccountry));

        Snackbar.make(parent,cname+" is registered",Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name.setText("");
                        email.setText("");
                        password.setText("");
                        repassword.setText("");
                        agreeCB.setChecked(false);
                        countrySpnr.setSelection(0);
                    }
                }).show();
    }

    @SuppressLint("SetTextI18n")
    private boolean validateFields() {
        int k=0;
        if(name.getText().toString().equals("")){
            warnName.setVisibility(View.VISIBLE);
            k=1;
        }
        else{
            warnName.setVisibility(View.GONE);
        }
        if(email.getText().toString().equals("")){
            warnEmail.setText("*Please enter your email");
            warnEmail.setVisibility(View.VISIBLE);
            k=1;
        }
        else{
            String checkEmail=email.getText().toString();
            boolean exists=false;
            for(Candidates c:candidateList){
                if(c.getEmail().equals(checkEmail)){
                    exists=true;
                    break;
                }
            }
            if(exists){
                warnEmail.setText("This email is already registered");
                warnEmail.setVisibility(View.VISIBLE);
                k=1;
            }
            else {
                warnEmail.setVisibility(View.GONE);
            }
        }
        if(password.getText().toString().equals("")){
            warnPass.setVisibility(View.VISIBLE);
            k=1;
        }
        else{
            warnPass.setVisibility(View.GONE);
        }
        if(repassword.getText().toString().equals("")){
            warnRepass.setText("*Please enter password again");
            warnRepass.setVisibility(View.VISIBLE);
            k=1;
        }
        else{
            if(!password.getText().toString().equals(repassword.getText().toString())) {
                warnRepass.setText("*Password in both fields do not match");
                warnRepass.setVisibility(View.VISIBLE);
                k=1;
            }
            else{
                warnRepass.setVisibility(View.GONE);
            }
        }
        if(countrySpnr.getSelectedItem()==null){
           warnCountry.setVisibility(View.VISIBLE);
            k=1;
        }
        else{
            warnCountry.setVisibility(View.GONE);
        }


        if(k==1) return false;
        return true;
    }
}