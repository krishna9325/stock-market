package krishnaapps.com.stockbreakouts.progress;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.progress.entity.Note;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getProfit() == newItem.getProfit() && oldItem.getStockName().equals(newItem.getStockName()) &&
                    oldItem.getProfitLoss() == newItem.getProfitLoss() && oldItem.getProgress().equals(newItem.getProgress());
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_progress, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.testStockName.setText(currentNote.getStockName());
        holder.textViewDescription.setText(currentNote.getProgress());
        if (currentNote.getProfit() == 1) {
            holder.stockProfitLoss.setText("PROFIT: " + currentNote.getProfitLoss());
            holder.stockProfitLoss.setTextColor(Color.parseColor("#008577"));
        } else if (currentNote.getProfit() == 2) {
            holder.stockProfitLoss.setText("LOSS: " + currentNote.getProfitLoss());
            holder.stockProfitLoss.setTextColor(Color.parseColor("#E4714D"));
        } else {
            holder.stockProfitLoss.setText("-");
            holder.stockProfitLoss.setTextColor(Color.parseColor("#008577"));
        }
//        String pathForImage = currentNote.getImagePath();
//        if(pathForImage != null && !pathForImage.equals("")) {
//
//            try {
//                File f=new File(pathForImage);
//                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//                holder.imageStockMistake.setImageBitmap(b);
//            }
//            catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//            }
//        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView testStockName;
        private TextView textViewDescription;
        private ImageView progressOption;
        private TextView stockProfitLoss;
        private CardView viewProgress;

        public NoteHolder(View itemView) {
            super(itemView);

            testStockName = itemView.findViewById(R.id.progress_stock_name);
            textViewDescription = itemView.findViewById(R.id.proress_stock_desc);
            stockProfitLoss = itemView.findViewById(R.id.progress_stock_profit_loss);
            progressOption = itemView.findViewById(R.id.progress_edit);
            viewProgress = itemView.findViewById(R.id.progress_view);

            progressOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.recyclerview_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.delete_menu:
                                    if (listener != null && position != RecyclerView.NO_POSITION) {
                                        listener.onDeleteNote(getItem(position));
                                    }
                                    return true;

                                case R.id.edit_menu:
                                    if (listener != null && position != RecyclerView.NO_POSITION) {
                                        listener.onEditCVClick(getItem(position));
                                    }
                                    return true;
                                default:
                                    return true;
                            }

                        }
                    });
                    popup.show();
                }
            });

            viewProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onCVItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onCVItemClick(Note note);

        void onEditCVClick(Note note);

        void onDeleteNote(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}