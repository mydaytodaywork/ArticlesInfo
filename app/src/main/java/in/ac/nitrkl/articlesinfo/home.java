package in.ac.nitrkl.articlesinfo;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home extends AppCompatActivity{
    String selectedItem;
    String[] text={"Featured Articles","Recent Articles","BranchWise Articles","Category Articles","My Published Articles","Write To Us","Contact Us","About Us"};
    int[] imageId = {
            R.drawable.featured,
            R.drawable.recent,
            R.drawable.branch,
            R.drawable.branch,
            R.drawable.published,
            R.drawable.writetous,
            R.drawable.contactus,
            R.drawable.about
    };

    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        grid = (GridView) findViewById(R.id.gridview);
        MyAdapter adapter = new MyAdapter(home.this, text, imageId);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(home.this, "You Clicked at " + position+"", Toast.LENGTH_SHORT).show();
                int selectedItem=position;
                if(selectedItem==0){
                    Intent i=new Intent(home.this,featuredarticles.class);
                    startActivity(i);
                }
                else if(selectedItem==1){
                    Intent i=new Intent(home.this,ourrecentarticles.class);
                    startActivity(i);
                }
                else if(selectedItem==2){
                    Intent i=new Intent(home.this,branchwise.class);
                    startActivity(i);
                }
                else if(selectedItem==3){
                    Intent i=new Intent(home.this,categorywise.class);
                    startActivity(i);
                }
                else if(selectedItem==4){
                    Intent i=new Intent(home.this,publishedarticles.class);
                    startActivity(i);
                }
                else if(selectedItem==5){
                    Intent i=new Intent(home.this,writetous.class);
                    startActivity(i);
                }
                else if(selectedItem==6){
                    Intent i=new Intent(home.this,contactus.class);
                    startActivity(i);
                }
                else if(selectedItem==7){
                    Intent i=new Intent(home.this,aboutus.class);
                    startActivity(i);
                }
            }
        });
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
                Intent i=new Intent(home.this,userprofile.class);
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
                Intent i=new Intent(home.this,searcharticles.class);
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