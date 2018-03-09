package sagar.khengat.gsmart.Adapters;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

;import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.util.MyAdapterListener;


public class CustomcheckOut extends RecyclerView.Adapter<CustomcheckOut.ViewHolder> {


    private Context context;
    public MyAdapterListener onClickListener;


    List<Cart> productList;
    Cart product;


    public CustomcheckOut(List<Cart> products, Context context, MyAdapterListener listener){
        super();
        //Getting all the superheroes
        this.productList = products;
//        this.alQuantity= q;
        this.context = context;
        this.onClickListener = listener;
    }
    public CustomcheckOut(Cart product, Context context){
        super();
        //Getting all the superheroes
        this.product = product;
//        this.alQuantity= q;
        this.context = context;
    }
    @Override
    public CustomcheckOut.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_checkout, parent, false);
        CustomcheckOut.ViewHolder viewHolder = new CustomcheckOut.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomcheckOut.ViewHolder holder, int position) {

        final Cart product =  productList.get(position);







        holder.textViewName.setText(product.getProductName());
        holder.textViewBrand.setText(product.getProductCategory().getCategoryName());
        holder.tvSize.setText(product.getProductSize());
        holder.tvUnit.setText(" "+product.getProductUnit());
        holder.tvUnitsell.setText("/ "+product.getProductUnit());
        holder.tvUnitact.setText("/ "+product.getProductUnit());
        holder.textViewSub.setText(product.getProductSubCategory().getSubCategoryName());
        holder.textActualPrice.setText(Double.toString(product.getProductOriginalPrice()));
        holder.textSellingPrice.setText(Double.toString(product.getProductGstPrice()));
        String stringdouble= Integer.toString(product.getProductQuantity());
        String stringPrice= Double.toString(product.getProductTotalPrice());
        holder.tvTotalPrice.setText(stringPrice);
        holder.tvQuantity.setText(stringdouble);


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
        public ImageView delete;
        public TextView textViewName;
        public TextView textViewBrand;
        public TextView textViewSub;
        public TextView textActualPrice;
        public TextView textSellingPrice;
        public TextView tvQuantity;
        public TextView tvUnit;
        public TextView tvUnitsell;
        public TextView tvUnitact;
        public TextView tvTotalPrice;
        public TextView tvOff;
        public TextView tvSize;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            textViewName = (TextView) itemView.findViewById(R.id.product_name);
            textViewBrand= (TextView) itemView.findViewById(R.id.product_category);
            textViewSub= (TextView) itemView.findViewById(R.id.product_subcategory);
            textActualPrice= (TextView) itemView.findViewById(R.id.actual_price);
            textSellingPrice= (TextView) itemView.findViewById(R.id.selling_price);
            tvQuantity = (TextView) itemView.findViewById(R.id.quantity);
            tvUnit = (TextView) itemView.findViewById(R.id.unit);
            tvUnitsell = (TextView) itemView.findViewById(R.id.unitsell);
            tvUnitact = (TextView) itemView.findViewById(R.id.unitact);
            tvTotalPrice = (TextView)itemView.findViewById(R.id.total_price);
            tvOff = (TextView)itemView.findViewById(R.id.off);
            tvSize = (TextView)itemView.findViewById(R.id.size);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.imageViewOnClick(v, getAdapterPosition());
                }
            });
        }
    }
}