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
import krishnaapps.com.stockbreakouts.stocks.model.Swing;

public class SwingAdapter extends RecyclerView.Adapter<SwingAdapter.SwingViewHolder> {
    private Context mContext;
    private List<Swing> mSwing;
    private SwingInterface swingInterface;

    public SwingAdapter(Context context, List<Swing> swing, SwingInterface swingInterface) {
        mContext = context;
        mSwing = swing;
        this.swingInterface = swingInterface;
    }

    @NonNull
    @Override
    public SwingAdapter.SwingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_swing, parent, false);
        return new SwingAdapter.SwingViewHolder(v, swingInterface);
    }

    @Override
    public void onBindViewHolder(SwingAdapter.SwingViewHolder holder, int position) {
        Swing swingCurrent = mSwing.get(position);

        holder.swingStockName.setText(swingCurrent.getSwingName());
        holder.swingStockDate.setText(swingCurrent.getSwingDate());

        Glide.with(mContext)
                .load(swingCurrent.getSwingImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(holder.swingImageView);

    }

    @Override
    public int getItemCount() {
        return mSwing.size();
    }

    public class SwingViewHolder extends RecyclerView.ViewHolder {
        public TextView swingStockName, swingStockDate;
        public ImageView swingImageView;

        public SwingViewHolder(View itemView, SwingInterface swingInterface) {
            super(itemView);

            swingStockName = itemView.findViewById(R.id.swing_stock_name);
            swingStockDate = itemView.findViewById(R.id.swing_stock_date);
            swingImageView = itemView.findViewById(R.id.swing_stock_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(swingInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            swingInterface.onClickSwing(position);
                        }
                    }
                }
            });
        }
    }

    public interface SwingInterface {
        void onClickSwing(int position);
    }

}
