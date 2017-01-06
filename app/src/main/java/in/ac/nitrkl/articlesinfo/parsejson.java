package in.ac.nitrkl.articlesinfo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kamal pc on 26-Oct-16.
 */

public class parsejson {
    public String[] article_title;
    public String[] article_author;
    public String[] article_time;
    public String[] article_shorttext;
    public int[] postid;

    public static final String JSON_ARRAY = "details";
    public static final String KEY_ID = "postid";
    public static final String KEY_TITLE = "topic";
    public static final String KEY_INFO = "shortinfo";
    public static final String KEY_TIME = "subtime";
    public static final String KEY_AUTHOR = "author";

    private JSONArray articles=null;
    private String json;

    public parsejson(String json){
        this.json=json;
    }
    public parsejson(){}

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            articles = jsonObject.getJSONArray(JSON_ARRAY);

            article_author = new String[articles.length()];
            article_time = new String[articles.length()];
            article_shorttext = new String[articles.length()];
            article_title = new String[articles.length()];
            postid = new int[articles.length()];

            for(int i=0;i<articles.length();i++){
                JSONObject jo = articles.getJSONObject(i);
                postid[i] = Integer.parseInt(jo.getString(KEY_ID));
                article_author[i] = jo.getString(KEY_AUTHOR);
                article_time[i] = jo.getString(KEY_TIME);
                article_shorttext[i] = jo.getString(KEY_INFO);
                article_title[i] = jo.getString(KEY_TITLE);
                //Log.d("JSONDATA",postid[i]+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
