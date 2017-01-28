package in.ac.nitrkl.articlesinfo;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class complete_article extends AppCompatActivity {
    String branch="",category="",topic="",longinfo="",author="",time="",link="",code_data="";
    String data[];
    TextView cbranch,ccategory,ctopic,clonginfo,cauthor,clink,ctime,ccodedata;
    String url="https://datacompiled.000webhostapp.com/completearticle.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_article);
        int id=getIntent().getIntExtra("id",0);
        url=url+"id="+id;
        getdata();

        cbranch=(TextView)findViewById(R.id.complete_branch);
        ccategory=(TextView)findViewById(R.id.complete_category);
        ctopic=(TextView)findViewById(R.id.complete_title);
        clonginfo=(TextView)findViewById(R.id.complete_info);
        cauthor=(TextView)findViewById(R.id.complete_author);
        clink=(TextView)findViewById(R.id.complete_links);
        ctime=(TextView)findViewById(R.id.complete_time);
        ccodedata=(TextView)findViewById(R.id.complete_code);
    }

    public void getdata(){
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Data",response.toString());
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(complete_article.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        JSONObject jsonObject=null;
        try {
            JSONArray articles=null;
            data=new String[10];
            jsonObject = new JSONObject(json);
            articles = jsonObject.getJSONArray("details");
            JSONObject jo = articles.getJSONObject(0);
            data[0] = jo.getString("branch");
            data[1]= jo.getString("kind");
            data[2] = jo.getString("topic");
            data[3] = jo.getString("longinfo");
            data[4]= jo.getString("author");
            data[5]= jo.getString("subtime");
            data[6]= jo.getString("links");
            data[7]= jo.getString("code_data");
            Log.d("Apptest1","xyz"+data[0]+" "+data[1]+" "+data[3]);
            cbranch.setText("Branch: "+data[0]);
            ccategory.setText("Category: "+data[1]);
            ctime.setText(data[5]);
            ctopic.setText(Html.fromHtml(data[2]));
            clonginfo.setText(Html.fromHtml(data[3]));
            cauthor.setText("By: "+data[4]);
            if(data[7].equals("") || data[7]==null){
                ccodedata.setVisibility(View.GONE);
            }
            else ccodedata.setText((data[7]));

            if(data[6].equals("") || data[6]==null){
                clink.setVisibility(View.GONE);
            }
            else clink.setText(data[6]);

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
                Intent i=new Intent(complete_article.this,userprofile.class);
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
                Intent i=new Intent(complete_article.this,searcharticles.class);
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
