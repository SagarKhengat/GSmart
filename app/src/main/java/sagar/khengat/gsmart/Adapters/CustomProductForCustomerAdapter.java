package sagar.khengat.gsmart.Adapters;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.util.MyAdapterListener;

/**
 * Created by sagar on 3/7/18.
 */

public class CustomProductForCustomerAdapter extends RecyclerView.Adapter<CustomProductForCustomerAdapter.ViewHolder> {


    private Context context;

    public MyAdapterListener onClickListener;
    List<Product> productList;
    Product product;


    public CustomProductForCustomerAdapter(List<Product> products, Context context,MyAdapterListener listener){
        super();
        //Getting all the superheroes
        this.productList = products;
//        this.alQuantity= q;
        this.context = context;
        this.onClickListener = listener;
    }
    public CustomProductForCustomerAdapter(Product product, Context context){
        super();
        //Getting all the superheroes
        this.product = product;
//        this.alQuantity= q;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product_for_customer, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Product product =  productList.get(position);






        holder.textViewName.setText(product.getProductName());
        holder.tvSize.setText(product.getProductSize());

        holder.tvUnitsell.setText("/ "+product.getProductUnit());
        holder.tvUnitact.setText("/ "+product.getProductUnit());

        holder.textActualPrice.setText(Double.toString(product.getProductOriginalPrice()));
        holder.textSellingPrice.setText(Double.toString(product.getProductGstPrice()));

        holder.tvTotalPrice.setText(product.getProductSubCategory().getSubCategoryName());
        holder.tvQuantity.setText(product.getProductCategory().getCategoryName());


        double a = new Double(product.getProductOriginalPrice());
        double b =new Double(product.getProductGstPrice()) ;


        double o = (b/a)*100;

        Double dd = new Double(100-o);
        float offf = dd.floatValue();
        DecimalFormat df = new DecimalFormat("#.##");
        String off =df.format(dd);

        holder.tvOff.setText(off+"% Off");
        if (offf==0)
        {
            holder.tvOff.setVisibility(View.INVISIBLE);
        }
        Picasso.with(context).load(new File(  Environment.getExternalStorageDirectory().getPath()
                + File.separator
                +"GSmart"+  File.separator
                + product.getStore().getStoreName()+File.separator+product.getProductName()+".jpg"))
                .placeholder(R.drawable.product)
                .fit()
                .into(holder.imageView);

    }

//    public void showCartDialog()
//    {
//
//
//    }





    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewName;

        public TextView textActualPrice;
        public TextView textSellingPrice;
        public TextView tvQuantity;

        public TextView tvUnitsell;
        public TextView tvUnitact;
        public TextView tvTotalPrice;
        public TextView tvOff;
        public TextView tvSize;
        public Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            textViewName = (TextView) itemView.findViewById(R.id.product_name);

            textActualPrice= (TextView) itemView.findViewById(R.id.actual_price);
            textSellingPrice= (TextView) itemView.findViewById(R.id.selling_price);
            tvQuantity = (TextView) itemView.findViewById(R.id.product_category);

            tvUnitsell = (TextView) itemView.findViewById(R.id.unitsell);
            tvUnitact = (TextView) itemView.findViewById(R.id.unitact);
            tvTotalPrice = (TextView)itemView.findViewById(R.id.product_subcategory);
            tvOff = (TextView)itemView.findViewById(R.id.off);
            tvSize = (TextView)itemView.findViewById(R.id.size);
            add = (Button) itemView.findViewById(R.id.add);



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.imageViewOnClick(v, getAdapterPosition());
                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.buttonViewOnClick(v, getAdapterPosition());
                }
            });
        }
    }
}



