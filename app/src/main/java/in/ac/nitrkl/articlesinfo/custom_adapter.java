package in.ac.nitrkl.articlesinfo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kamal pc on 26-Oct-16.
 */

public class custom_adapter extends ArrayAdapter<String>{

    private String[] article_title;
    private String[] article_author;
    private String[] article_time;
    private String[] article_shorttext;
    private Activity context;

    custom_adapter(Activity context,String[] title, String[] author, String[] time, String[] shorttext){
        super(context,R.layout.custom_row);
        this.context=context;
        article_title=title;
        article_author=author;
        article_time=time;
        article_shorttext=shorttext;
        /*for(int i=0;i<article_author.length;i++)
            Log.d("Author",article_author[i]);*/
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView=LayoutInflater.from(getContext()).inflate(R.layout.custom_row,parent,false);;

        TextView tv_title=(TextView)customView.findViewById(R.id.title_show);
        TextView tv_author=(TextView)customView.findViewById(R.id.author_show);
        TextView tv_time=(TextView)customView.findViewById(R.id.datetime_show);
        TextView tv_shorttext=(TextView)customView.findViewById(R.id.shortinfo_show);

        //Log.d("DataOnebyOne",article_title[position]);
        tv_title.setText(Html.fromHtml(article_title[position]));
        tv_author.setText(Html.fromHtml(article_author[position]));
        tv_shorttext.setText(Html.fromHtml(article_shorttext[position]));
        tv_time.setText(Html.fromHtml(article_time[position]));


        return customView;
    }

    @Override
    public int getCount() {
        return article_author.length;
    }
}
