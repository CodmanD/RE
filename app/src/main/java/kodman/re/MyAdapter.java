package kodman.re;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kodman.re.Models.Product;

/**
 * Created by dd on 25.02.2018.
 */

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Product> products;

    public MyAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product post = products.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //holder.post.setText(Html.fromHtml(post.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            //holder.post.setText(Html.fromHtml(post.getElementPureHtml()));
        }
       // holder.site.setText(post.getSite());
    }

    @Override
    public int getItemCount() {
        if ( products == null)
            return 0;
        return  products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView post;
        TextView site;

        public ViewHolder(View itemView) {
            super(itemView);
        //    post = (TextView) itemView.findViewById(R.id.postitem_post);
        //    site = (TextView) itemView.findViewById(R.id.postitem_site);
        }
    }
}
