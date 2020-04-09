package kolware.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends BaseAdapter{
    private Context context;
    private List<AppList> listStorage;
    public AppAdapter(Context context,List<AppList> listStorage)
    {
        this.context=context;
        this.listStorage=listStorage;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int i) {
        return listStorage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.apps, parent, false);
        }
        AppList currentitem=(AppList)getItem(position);
        TextView appname=(TextView)view.findViewById(R.id.list_app_name);
        appname.setText(currentitem.getName());
        ImageView appicon=(ImageView)view.findViewById(R.id.app_icon);
        appicon.setImageDrawable(currentitem.getIcon());

        return view;
    }
}