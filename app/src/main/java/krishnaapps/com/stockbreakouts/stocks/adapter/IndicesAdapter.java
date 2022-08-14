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
import krishnaapps.com.stockbreakouts.stocks.model.Indices;

public class IndicesAdapter extends RecyclerView.Adapter<IndicesAdapter.IndicesViewHolder> {
    private Context mContext;
    private List<Indices> mIndices;
    private IndicesInterface indicesInterface;
    public IndicesAdapter(Context context, List<Indices> indices, IndicesInterface indicesInterface) {
        mContext = context;
        mIndices = indices;
        this.indicesInterface = indicesInterface;
    }

    @NonNull
    @Override
    public IndicesAdapter.IndicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_indices, parent, false);
        return new IndicesAdapter.IndicesViewHolder(v, indicesInterface);
    }

    @Override
    public void onBindViewHolder(IndicesAdapter.IndicesViewHolder holder, int position) {
        Indices indicesCurrent = mIndices.get(position);

        holder.indicesStockName.setText(indicesCurrent.getIndicesName());
        holder.indicesStockDate.setText(indicesCurrent.getIndicesDate());

        Glide.with(mContext)
                .load(indicesCurrent.getIndicesImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(holder.indicesImageView);

    }

    @Override
    public int getItemCount() {
        return mIndices.size();
    }

    public class IndicesViewHolder extends RecyclerView.ViewHolder {
        public TextView indicesStockName, indicesStockDate;
        public ImageView indicesImageView;

        public IndicesViewHolder(View itemView, IndicesInterface indicesInterface) {
            super(itemView);

            indicesStockName = itemView.findViewById(R.id.indices_stock_name);
            indicesStockDate = itemView.findViewById(R.id.indices_stock_date);
            indicesImageView = itemView.findViewById(R.id.indices_stock_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(indicesInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            indicesInterface.onClickIndices(position);
                        }
                    }
                }
            });
        }
    }

    public interface IndicesInterface {
        void onClickIndices(int position);
    }

}
