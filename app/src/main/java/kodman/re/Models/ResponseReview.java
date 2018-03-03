package kodman.re.Models;

/**
 * Class Response from Server with Review
 */

public class ResponseReview {
    int id;
    int product;
    User created_by;
    int rate;
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public User getCreated_by() {
        return created_by;
    }

    public void setCreated_by(User creted_by) {
        this.created_by = creted_by;
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

    @Override
    public String toString(){
        return created_by.getUsername()+": Rate="+rate+"|text"+text;
    }
}
