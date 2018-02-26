package kodman.re.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dd on 26.02.2018.
 */

public class Comment {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("rate")
    @Expose
    private String rate;//рейтинг

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("id_user")
    @Expose
    private String id_user;//идентификатор пользоваеля

    @SerializedName("id_entry")
    @Expose
    private String id_entry;//идентификатор продукта

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_entry() {
        return id_entry;
    }

    public void setId_entry(String id_entry) {
        this.id_entry = id_entry;
    }
}

