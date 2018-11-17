package xyz.zzyymaggie.java.html;

public class Record {

    public static final int COLUMN_NUMBER = 5;
    String name;
    
    String url;
    
    String title;
    
    String keywords;
    
    String description;
    
    public Record(String url) {
        this.url = url;
    }
    
    public Record(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String toString() {
        return "\"" + name + "\",\"" + url + "\",\"" + title + "\",\"" + description + "\"";
    }
    
    public String[] getCSVContent() {
        String[] result = new String[COLUMN_NUMBER];
        result[0] = name;
        result[1] = url;
        result[2] = title;
        result[3] = description;
        result[4] = keywords;
        return result;
    }
}
