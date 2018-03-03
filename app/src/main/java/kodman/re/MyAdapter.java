package kodman.re;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kodman.re.Constants.Constants;
import kodman.re.Models.Product;

import static android.content.ContentValues.TAG;

/**
 * Created by dd on 25.02.2018.
 */

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



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //holder.post.setText(Html.fromHtml(post.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
//                Log.d(Constants.MAIN_TAG," onBindHol;der");
//
//          //  Glide.with(context)
//           //         .load(Constants.PATH_IMAGE+"img"+product.getId()+".png")
//            //        .into(holder.iv);
//
//            holder.tvTitle.setText(product.getTitle());
//        } else {
//            //holder.post.setText(Html.fromHtml(post.getElementPureHtml()));
//        }
       // holder.site.setText(post.getSite());
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

            //Log.d("------------ViewHolder","ImageView"+iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Product product= products.get(position);
                    Toast.makeText(context,"Open ID="+product.getId(),Toast.LENGTH_SHORT).show();
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
