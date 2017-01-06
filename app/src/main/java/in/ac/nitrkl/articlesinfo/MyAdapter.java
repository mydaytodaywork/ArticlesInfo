package in.ac.nitrkl.articlesinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kamal pc on 31-Oct-16.
 */
public  class MyAdapter extends BaseAdapter {
    Context mContext;
    String[] text;
    int[] Imageid;

    public MyAdapter(Context c,String[] text,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.text = text;
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return text[i];
    }

    @Override
    public long getItemId(int i) {
        return Imageid[i];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = new View(mContext);
            view = inflater.inflate(R.layout.grid_viewitem, null);
        }

        ImageView picture = (ImageView) view.findViewById(R.id.grid_image);
        TextView name = (TextView) view.findViewById(R.id.grid_text);

        picture.setImageResource(Imageid[position]);
        name.setText(text[position]);

        return view;
    }
}
