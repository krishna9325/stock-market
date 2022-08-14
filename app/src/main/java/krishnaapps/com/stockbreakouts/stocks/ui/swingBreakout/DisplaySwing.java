package krishnaapps.com.stockbreakouts.stocks.ui.swingBreakout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.model.Indices;
import krishnaapps.com.stockbreakouts.stocks.model.Swing;

public class DisplaySwing extends AppCompatActivity {

    TextView swingNameDisplay, swingDateDisplay, swingDiscripitionDisplay;
    ImageView backBtnSwing, swingImageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_swing);
        swingNameDisplay = findViewById(R.id.swing_display_name);
        swingDateDisplay = findViewById(R.id.swing_display_date);
        swingDiscripitionDisplay = findViewById(R.id.swing_display_description);
        swingImageDisplay = findViewById(R.id.swing_display_image);
        backBtnSwing = findViewById(R.id.swing_display_back);

        backBtnSwing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Swing swing = getIntent().getParcelableExtra("DisplaySwing");
        swingNameDisplay.setText(swing.getSwingName());
        String date = "Date: " + swing.getSwingDate();
        swingDiscripitionDisplay.setText(swing.getSwingDesc());
        swingDateDisplay.setText(date);


        Glide.with(this)
                .load(swing.getSwingImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(swingImageDisplay);
    }
}