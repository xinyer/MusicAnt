package wx.musicant;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends BaseAdapter {

    private Context context;

    public SongListAdapter(Context context) {
        this.context = context;
    }

    private List<Song> songList = new ArrayList<>();

    public void update(List<Song> list) {
        this.songList.clear();
        this.songList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Song getItem(int pos) {
        return songList.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_song, null);
            holder.tvName = convertView.findViewById(R.id.tv_song_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Song song = getItem(position);
        holder.tvName.setText(song.getName());
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
    }
}
