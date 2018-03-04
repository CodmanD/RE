package kodman.re;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import kodman.re.Constants.Constants;
import kodman.re.Models.Product;


public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<Product> products;
    private Context context;

    public MyAdapter(Context context,ArrayList<Product> products)
    {
        this.context=context;
        this.products = products;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        Log.d(Constants.MAIN_TAG,"-------------"+product);
        holder.tvTitle.setText(product.getTitle());
                    Glide.with(context)
                    .load(Constants.PATH_IMAGE+"img"+product.getId()+".png")
                    .thumbnail(0.5f)
                    .fitCenter()
                    .into(holder.iv);
                    holder.position=position;

    }

    @Override
    public int getItemCount() {
        if ( products == null)
            return 0;
        return  products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tvTitle;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);
            this.iv=  itemView.findViewById(R.id.ivSmallFoto);
            this.tvTitle =itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Product product= products.get(position);
                    Intent intent= new Intent(context,ScreenProduct.class);
                    intent.putExtra(Constants.ID, product.getId());
                    intent.putExtra(Constants.TEXT, product.getText());
                    intent.putExtra(Constants.TITLE, product.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
}
