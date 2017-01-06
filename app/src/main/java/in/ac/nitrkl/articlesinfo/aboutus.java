package in.ac.nitrkl.articlesinfo;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        TextView foo = (TextView)findViewById(R.id.aboutusid);
        foo.setText(Html.fromHtml(getString(R.string.aboutushtml)));
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
                Intent i=new Intent(aboutus.this,userprofile.class);
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
                Intent i=new Intent(aboutus.this,searcharticles.class);
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
