package krishnaapps.com.stockbreakouts.stocks.ui.intradaybreakout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.model.IntraDay;

public class DisplayIntraday extends AppCompatActivity {

    TextView intraDayNameDisplay, intraDayDateDisplay, intraDayDiscripitionDisplay;
    ImageView backBtnIntraDay, intraDayImageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_intraday);
        intraDayNameDisplay = findViewById(R.id.intraday_display_name);
        intraDayDateDisplay = findViewById(R.id.intraday_display_date);
        intraDayDiscripitionDisplay = findViewById(R.id.intraday_display_description);
        intraDayImageDisplay = findViewById(R.id.intraday_display_image);
        backBtnIntraDay = findViewById(R.id.intraday_display_back);

        backBtnIntraDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        IntraDay intraDay = getIntent().getParcelableExtra("DisplayIntraDay");
        intraDayNameDisplay.setText(intraDay.getName());
        String date = "Date: " + intraDay.getDate();
        intraDayDiscripitionDisplay.setText(intraDay.getDesc());
        intraDayDateDisplay.setText(date);


        Glide.with(this)
                .load(intraDay.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.failtoload)
                .into(intraDayImageDisplay);
    }
}