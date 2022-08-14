package krishnaapps.com.stockbreakouts.stocks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.model.IntraDay;

public class IntraDayAdapter extends RecyclerView.Adapter<IntraDayAdapter.ImageViewHolder> {
    private Context mContext;
    private List<IntraDay> mIntraDays;
    private IntradayInterface intradayInterface;

    public IntraDayAdapter(Context context, List<IntraDay> intraDays, IntradayInterface intradayInterface) {
        mContext = context;
        mIntraDays = intraDays;
        this.intradayInterface = intradayInterface;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_intraday, parent, false);
        return new ImageViewHolder(v, intradayInterface);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        IntraDay intraDayCurrent = mIntraDays.get(position);

        holder.textViewStockName.setText(intraDayCurrent.getName());
        holder.textViewStockDate.setText(intraDayCurrent.getDate());

        Glide.with(mContext)
                .load(intraDayCurrent.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mIntraDays.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewStockName, textViewStockDate;
        public ImageView imageView;

        public ImageViewHolder(View itemView, IntradayInterface intradayInterface) {
            super(itemView);

            textViewStockName = itemView.findViewById(R.id.intra_day_stock_name);
            textViewStockDate = itemView.findViewById(R.id.intra_day_stock_date);
            imageView = itemView.findViewById(R.id.intra_day_stock_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(intradayInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            intradayInterface.onClickIntraday(position);
                        }
                    }
                }
            });
        }
    }

    public interface IntradayInterface {
        void onClickIntraday(int position);
    }
}