package wx.musicant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "Main";

    private EditText etUrl;
    private Button btnParse;
    private ListView lvSongs;
    private MeowWebView webView;

    private SongListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        etUrl = findViewById(R.id.et_url);
        btnParse = findViewById(R.id.btn_parse);
        lvSongs = findViewById(R.id.lv_songs);
        webView = findViewById(R.id.webview);
        btnParse.setOnClickListener(this);
        adapter = new SongListAdapter(this);
        lvSongs.setAdapter(adapter);
        lvSongs.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url = "";

//        String url = etUrl.getText().toString();
//        url = trimInput(url);
//        new AsyncTask<String, Void, List<Song>>(){
//            @Override
//            protected List<Song> doInBackground(String... strings) {
//                return parseUrl(strings[0]);
//            }
//
//            @Override
//            protected void onPostExecute(List<Song> list) {
//                super.onPostExecute(list);
//                adapter.update(list);
//            }
//        }.execute(url);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Song song = adapter.getItem(i);
        if (song != null && song.getUrl() != null) {
            webView.loadUrl("http://music.163.com/m" + song.getUrl());
        }
    }

    private String trimInput(String input) {
        if (TextUtils.isEmpty(input)) return "";
        int start = input.indexOf("http://");
        int end = input.lastIndexOf("(来自");
        return input.substring(start, end);
    }

    private List<Song> parseUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements songs = doc.select("a[href*=/song?id=]");
            List<Song> songList = new ArrayList<>(songs.size());
            for (int i=0; i< songs.size(); i++) {
                Element song = songs.get(i);
                String hrefValue = song.attr("href");
                String text = song.text();
                songList.add(new Song(text, hrefValue));
            }
            return songList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
