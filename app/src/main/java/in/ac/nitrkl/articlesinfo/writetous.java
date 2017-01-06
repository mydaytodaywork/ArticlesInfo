package in.ac.nitrkl.articlesinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class writetous extends AppCompatActivity {

    SessionManagement session;
    EditText et_title,et_article,et_links;
    Spinner sp_category,sp_branch;
    String title,article,links,category,branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writetous);
        et_title=(EditText)findViewById(R.id.title);
        et_article=(EditText)findViewById(R.id.article);
        et_links=(EditText)findViewById(R.id.links);
        sp_category=(Spinner) findViewById(R.id.category);
        sp_branch=(Spinner) findViewById(R.id.branch);
        handleSSLHandshake();
        session=new SessionManagement(this);

        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemClick(AdapterView<?> parent,View view,int pos,long id){

            }
            public void onNothingSelected(AdapterView<?> arg0){
            }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                category=parent.getItemAtPosition(pos).toString();
                //Toast.makeText(getApplicationContext(),category, Toast.LENGTH_SHORT).show();
            }
        });

        sp_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemClick(AdapterView<?> parent,View view,int pos,long id){

            }
            public void onNothingSelected(AdapterView<?> arg0){
            }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                branch=parent.getItemAtPosition(pos).toString();
                //Toast.makeText(getApplicationContext(),branch, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void reset_message(View v){
        et_title.setText("");
        et_article.setText("");
        et_links.setText("");
    }

    public void send_message(View v){
        title=et_title.getText().toString();
        links=et_links.getText().toString();
        article=et_article.getText().toString();



        if(title.equals("") || links.equals("") || article.equals("") ){
            Toast.makeText(getApplicationContext(), "Fill up all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://192.168.43.68/articleinfo/writetous.php";
            final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response.toString());
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
                    params.put("title", title);
                    params.put("category", category);
                    params.put("branch", branch);
                    params.put("article", article);
                    params.put("links", links);
                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManagement.KEY_NAME);
                    params.put("username",username);
                    return params;
                }
            };
            queue.add(postRequest);
        }
        Toast.makeText(getApplicationContext(),"Successfully Submitted",Toast.LENGTH_SHORT).show();
        et_links.setText("");
        et_article.setText("");
        et_title.setText("");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                SessionManagement session=new SessionManagement(this);
                session.logoutUser();
                finish();
                break;
            case R.id.action_profile:
                Intent i=new Intent(writetous.this,userprofile.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Help menu item pressed", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        getMenuInflater().inflate(R.menu.menu,menus);
        MenuItem searchItem = menus.findItem(R.id.action_search);
        SearchView search = (SearchView) MenuItemCompat.getActionView(searchItem);
        search.setQueryHint("Search For Articles");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String arg0) {
                Intent i=new Intent(writetous.this,searcharticles.class);
                i.putExtra("searchword",arg0);
                startActivity(i);
                return true;
            }

            @Override
            public
            boolean onQueryTextChange(String arg0) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menus);
    }


}
