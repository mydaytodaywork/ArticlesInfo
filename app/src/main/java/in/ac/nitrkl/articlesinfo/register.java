package in.ac.nitrkl.articlesinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class register extends AppCompatActivity {
    String username, name, email, gender, phoneno, dob, password, confirm_password, profession, organization;
    EditText et_username, et_name, et_password, et_email, et_confirm_password, et_profession, et_organization, et_gender, et_phone, et_dob;
    boolean inserted;
    EditText emailValidate;
    String em;
    String emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        et_email = (EditText) findViewById(R.id.email);
        et_confirm_password = (EditText) findViewById(R.id.confirm_password);
        et_profession = (EditText) findViewById(R.id.profession);
        et_organization = (EditText) findViewById(R.id.organization);
        et_gender= (EditText) findViewById(R.id.gender);
        et_phone= (EditText) findViewById(R.id.phoneno);
        et_dob= (EditText) findViewById(R.id.dob);
        et_name= (EditText) findViewById(R.id.name);
        handleSSLHandshake();

        emailValidate = (EditText)findViewById(R.id.email);
        em = emailValidate.getText().toString().trim();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    }

    public void registerme(View v) {

        username = et_username.getText().toString();
        password = et_password.getText().toString();
        email = et_email.getText().toString();
        confirm_password = et_confirm_password.getText().toString();
        profession = et_profession.getText().toString();
        organization = et_organization.getText().toString();
        gender= et_gender.getText().toString();
        phoneno= et_phone.getText().toString();
        dob= et_dob.getText().toString();
        name= et_name.getText().toString();

        //Toast.makeText(getApplicationContext(),username+" "+password+" "+confirm_password+" ",Toast.LENGTH_SHORT).show();
        if(username.equals("") || password.equals("") || email.equals("") || profession.equals("") || organization.equals("")
                || confirm_password.equals("") || gender.equals("") || phoneno.equals("") || dob.equals("") || name.equals("")){
            Toast.makeText(getApplicationContext(), "Fill up all the fields.", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirm_password)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
        } else {
            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://datacompiled.000webhostapp.com/register.php";
            final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response.toString());
                            if(response.equals("0"))
                                Toast.makeText(getApplicationContext(),"Username or Email Exists.",Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(getApplicationContext(),"Successfully Registered. Login to Enter",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(register.this,LoginActivity.class);
                                startActivity(i);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("name", name);
                    params.put("password", password);
                    params.put("email", email);
                    params.put("organization", organization);
                    params.put("profession", profession);
                    params.put("gender", gender);
                    params.put("phone", phoneno);
                    params.put("dob", dob);
                    return params;
                }
            };
            queue.add(postRequest);
        }

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
