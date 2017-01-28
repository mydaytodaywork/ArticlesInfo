package in.ac.nitrkl.articlesinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class userprofile extends AppCompatActivity {
    SessionManagement session;
    String username, name, dob, gender, profession, organization, phoneno, email, joiningdate;
    String url="https://datacompiled.000webhostapp.com/getprofile.php?";
    TextView tname,tdob,tgender,tprofession,torganization,tphoneno,temail,tjoiningdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        tname=(TextView)findViewById(R.id.name);
        tdob=(TextView)findViewById(R.id.dob);
        tgender=(TextView)findViewById(R.id.gender);
        tprofession=(TextView)findViewById(R.id.profession);
        torganization=(TextView)findViewById(R.id.organization);
        tphoneno=(TextView)findViewById(R.id.phno);
        temail=(TextView)findViewById(R.id.emailid);
        tjoiningdate=(TextView)findViewById(R.id.joining_date);

        session=new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManagement.KEY_NAME);
        url=url+"username="+username;
        getdata();
    }

    public void getdata(){
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Data",response.toString());
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(userprofile.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        JSONObject jsonObject=null;
        try {
            JSONArray articles=null;
            jsonObject = new JSONObject(json);
            articles = jsonObject.getJSONArray("details");
            JSONObject jo = articles.getJSONObject(0);
            name= jo.getString("name");
            dob= jo.getString("dob");
            gender= jo.getString("gender");
            profession= jo.getString("profession");
            organization= jo.getString("organization");
            phoneno= jo.getString("phone");
            email= jo.getString("email");
            joiningdate= jo.getString("joiningdate");
            tname.setText(name);
            tdob.setText("DOB: "+dob);
            tgender.setText("Gender: "+gender);
            tprofession.setText(profession);
            torganization.setText("at "+organization);
            tphoneno.setText("Phno: "+phoneno);
            temail.setText("emailid:"+email);
            tjoiningdate.setText("Joined On:"+joiningdate);

        } catch (JSONException e) {
            e.printStackTrace();
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
                Intent i=new Intent(userprofile.this,userprofile.class);
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
                Intent i=new Intent(userprofile.this,searcharticles.class);
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
