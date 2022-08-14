package krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.model.Indices;

public class DisplayIndices extends AppCompatActivity {

    TextView indicesNameDisplay, indicesDateDisplay, indicesDiscripitionDisplay;
    ImageView backBtnIndices, indicesImageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_indices);
        indicesNameDisplay = findViewById(R.id.indices_display_name);
        indicesDateDisplay = findViewById(R.id.indices_display_date);
        indicesDiscripitionDisplay = findViewById(R.id.indices_display_description);
        indicesImageDisplay = findViewById(R.id.indices_display_image);
        backBtnIndices = findViewById(R.id.indices_display_back);

        backBtnIndices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Indices indices = getIntent().getParcelableExtra("DisplayIndices");
        indicesNameDisplay.setText(indices.getIndicesName());
        String date = "Date: " + indices.getIndicesDate();
        indicesDiscripitionDisplay.setText(indices.getIndicesDesc());
        indicesDateDisplay.setText(date);


        Glide.with(this)
                .load(indices.getIndicesImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(indicesImageDisplay);
    }
}