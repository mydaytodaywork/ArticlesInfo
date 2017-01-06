package in.ac.nitrkl.articlesinfo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText et_username,et_password;
    String username,password;
    int loggedin;
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handleSSLHandshake();
        et_username=(EditText)findViewById(R.id.username);
        et_password=(EditText)findViewById(R.id.password);

        session=new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManagement.KEY_NAME);

        //uncomment this to start session part
        if(name!=null){
            Intent i=new Intent(LoginActivity.this,home.class);
            startActivity(i);
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void regist(View v){
        Intent i=new Intent(LoginActivity.this,register.class);
        startActivity(i);
    }

    public void login(View v){
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        //Toast.makeText(getApplicationContext(), username+ " "+password, Toast.LENGTH_SHORT).show();
        if(username.equals("") || password.equals("")  ){
            Toast.makeText(getApplicationContext(), "Fill up all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://192.168.43.68/articleinfo/login.php";
            final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response.toString());
                            loggedin=Integer.parseInt(response.toString());
                            Log.d("LoggedIn"," "+loggedin);
                            //Toast.makeText(getApplicationContext(),loggedin+" adbah",Toast.LENGTH_SHORT).show();
                            if(loggedin==0){
                                 Toast.makeText(getApplicationContext(),"Username doesn't Exist",Toast.LENGTH_SHORT).show();
                            }
                            else if(loggedin==1){
                                Toast.makeText(getApplicationContext(),"Invalid Password.",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent i=new Intent(LoginActivity.this,home.class);
                                startActivity(i);
                                session.createLoginSession(username,"articleinfo");
                                //Toast.makeText(getApplicationContext(),"Successful.",Toast.LENGTH_SHORT).show();
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
                    params.put("password", password);
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

