package dam.isi.frsf.laboratorio03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by mramos on 19/09/17.
 */

public class MyAdapter extends BaseAdapter {
    LayoutInflater inflater;

    MyAdapter(Context context, List<Trabajo> items) {
        super(context, R.layout.row1, items);
        inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=inflater.inflate(R.layout.row1, parent, false);
        return(row);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
