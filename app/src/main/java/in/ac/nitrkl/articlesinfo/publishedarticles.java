package in.ac.nitrkl.articlesinfo;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;

public class publishedarticles extends AppCompatActivity {
    ListView lv;
    int ids[];
    parsejson pjson;
    String url = "http://192.168.43.68/articleinfo/searcharticles.php?";
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishedarticles);

        session=new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManagement.KEY_NAME);
        url=url+"user="+username;

        lv=(ListView)findViewById(R.id.publishedlist);
        getdata();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(publishedarticles.this,complete_article.class);
                i.putExtra("id",ids[position]);
                startActivity(i);
            }
        });
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
                        Toast.makeText(publishedarticles.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        parsejson pj = new parsejson(json);
        pj.parseJSON();
        pjson=new parsejson(json);
        pjson.parseJSON();
        ids=new int[pjson.postid.length];
        ids= Arrays.copyOf(pjson.postid,pjson.postid.length);
        if(ids.length==0){
            Toast.makeText(getApplicationContext(),"No Articles Found",Toast.LENGTH_SHORT).show();
        }
        custom_adapter cl = new custom_adapter(this,pj.article_title,pj.article_author,pj.article_time,pj.article_shorttext);
        lv.setAdapter(cl);
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
                Intent i=new Intent(publishedarticles.this,userprofile.class);
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
                Intent i=new Intent(publishedarticles.this,searcharticles.class);
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
