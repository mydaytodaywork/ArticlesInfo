package in.ac.nitrkl.articlesinfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by kamal pc on 01-Nov-16.
 */

public class itemlist_adapter extends ArrayAdapter<String>{
    private String[] itemlist_array;
    private Activity context;

    itemlist_adapter(Activity context,String[] branches){
        super(context,R.layout.custom_list_view_layout);
        this.context=context;
        itemlist_array=branches;
        /*for(int i=0;i<article_author.length;i++)
            Log.d("Author",article_author[i]);*/
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row,parent,false);;

        TextView tv_title=(TextView)customView.findViewById(R.id.itemlist);

        //Log.d("DataOnebyOne",article_title[position]);
        tv_title.setText(Html.fromHtml(itemlist_array[position]));
        return customView;
    }

    @Override
    public int getCount() {
        return itemlist_array.length;
    }

}
