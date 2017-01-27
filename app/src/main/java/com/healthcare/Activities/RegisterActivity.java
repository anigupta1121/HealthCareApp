package com.healthcare.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthcare.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etUserName,etPassword,etPasswordAgain,etEmail;
    String Name,UserName,Password,PasswordAgain,Email;
    SmallBang mSmallBang;
    TextView tvRegister;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mSmallBang= SmallBang.attach2Window(this);

        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetConnected())
                    invokeRegister(btnRegister);
                else
                    Snackbar.make(findViewById(R.id.registerLinearLayout),"Check your internet connection!",Snackbar.LENGTH_LONG)
                            .setAction("Ok",null)
                            .show();

            }

        });

        etName=(EditText) findViewById(R.id.editTextName);
        etUserName=(EditText)findViewById(R.id.editTextUserName);
        etPassword=(EditText)findViewById(R.id.editTextPassword);
        etPasswordAgain=(EditText)findViewById(R.id.editTextPasswordAgain);
        etEmail=(EditText)findViewById(R.id.editTextEmail);

        tvRegister=(TextView)findViewById(R.id.tvRegister);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/font.ttf");
        tvRegister.setTypeface(tf);


    }
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
    public void invokeRegister(View view) {
        Name=etName.getText().toString();
        UserName=etUserName.getText().toString();
        Password=etPassword.getText().toString();
        PasswordAgain=etPasswordAgain.getText().toString();
        Email=etEmail.getText().toString();

        if(!(Name.equals("")||UserName.equals("")||Password.equals("")||PasswordAgain.equals("")||Email.equals("")))
        {
            if(Password.equals(PasswordAgain))
            {
                if(isValidEmail(Email))
                {
                    mSmallBang.bang(view,new SmallBangListener() {
                        @Override
                        public void onAnimationStart() {
                            register();
                        }

                        @Override
                        public void onAnimationEnd() {

                        }
                    });

                }
                else {
                    etEmail.setText("");
                    Snackbar.make(findViewById(R.id.registerLinearLayout),"Enter valid email!",Snackbar.LENGTH_LONG)
                            .setAction("Ok",null)
                            .show();


                }

            }
            else {
                etPassword.setText("");
                etPasswordAgain.setText("");
                Snackbar.make(findViewById(R.id.registerLinearLayout),"Passwords do not match!",Snackbar.LENGTH_LONG)
                        .setAction("Ok",null)
                        .show();

            }


        }
        else
            Snackbar.make(findViewById(R.id.registerLinearLayout),"Please enter all the details!",Snackbar.LENGTH_LONG)
                    .setAction("Ok",null)
                    .show();

    }

    private void register() {
        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(RegisterActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
                String uname=params[1];
                String pass = params[2];
                String email=params[3];


                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("name", name));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://anigupta.esy.es/register.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(RegisterActivity.this,  LoginActivity.class);

                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        }

        RegisterAsync la = new RegisterAsync();
        la.execute(Name,UserName,Password,Email);

    }


    boolean isValidEmail(String e){
        for(int i=0;i<e.length();i++)
        {
            if(e.charAt(i)=='@')
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this,  LoginActivity.class);
        finish();
        startActivity(intent);
    }
}
