package kodman.re.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dd on 04.03.2018.
 */

public class Review {


    @SerializedName("rate")
    @Expose
    private int rate;

    @SerializedName("text")
    @Expose
     private String text;

    public Review(int rate, String text) {
        this.rate = rate;
        this.text = text;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
