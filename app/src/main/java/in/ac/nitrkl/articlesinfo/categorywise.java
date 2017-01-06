package in.ac.nitrkl.articlesinfo;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class categorywise extends AppCompatActivity {
    ListView lv;
    String[] article_category;
    String[] article_counter;
    int[] categoryid;
    final String url = "http://192.168.43.68/articleinfo/allcategory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorywise);

        lv=(ListView)findViewById(R.id.category_list);
        getdata();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),categoryid[position]+"",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(categorywise.this,categoryarticles.class);
                i.putExtra("categoryid",categoryid[position]);
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
                        Toast.makeText(categorywise.this,error.getMessage(),Toast.LENGTH_LONG).show();
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
            article_category = new String[articles.length()];
            article_counter = new String[articles.length()];
            categoryid=new int[articles.length()];

            for(int i=0;i<articles.length();i++){
                JSONObject jo = articles.getJSONObject(i);
                article_category[i] = jo.getString("category")+" ("+jo.getString("counter")+")";
                categoryid[i]=Integer.parseInt(jo.getString("id"));
                //Log.d("JSONDATA",article_counter[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,article_category));
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
                Intent i=new Intent(categorywise.this,userprofile.class);
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
                Intent i=new Intent(categorywise.this,searcharticles.class);
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