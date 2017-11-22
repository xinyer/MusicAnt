package wx.musicant;


public class Song {

    private String name;
    private String url;

    public Song(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
