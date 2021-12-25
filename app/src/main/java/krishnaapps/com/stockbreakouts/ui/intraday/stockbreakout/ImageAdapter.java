package krishnaapps.com.stockbreakouts.ui.intraday.stockbreakout;

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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_intraday, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);

        holder.textViewStockName.setText(uploadCurrent.getName());
        holder.textViewStockDate.setText(uploadCurrent.getDate());

        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewStockName, textViewStockDate;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewStockName = itemView.findViewById(R.id.intra_day_stock_name);
            textViewStockDate = itemView.findViewById(R.id.intra_day_stock_date);
            imageView = itemView.findViewById(R.id.intra_day_stock_image);
        }
    }
}