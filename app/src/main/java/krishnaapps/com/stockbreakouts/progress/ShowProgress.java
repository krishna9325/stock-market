package krishnaapps.com.stockbreakouts.progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import krishnaapps.com.stockbreakouts.databinding.ActivityShowPrgressBinding;

import static krishnaapps.com.stockbreakouts.Constants.EXTRA_ID;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_IMAGE_PATH;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_MISTAKES;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_PROFIT;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_PROFIT_LOSS;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_PROGRESS;
import static krishnaapps.com.stockbreakouts.Constants.EXTRA_STOCK_NAME;

public class ShowProgress extends AppCompatActivity {

    TextView showProgress, showStockName, showMistake, showProfit, progressTextView, mistakeTextView;
    ImageView showImage, backShow;
    CardView cardFoImage;

    ActivityShowPrgressBinding binding;

    private int profitLoss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPrgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showImage = binding.showStockImage;
        showMistake = binding.showStockMistake;
        showStockName = binding.showStockName;
        showProfit = binding.showStockProfitLoss;
        showProgress = binding.showStockProgress;
        progressTextView = binding.progress;
        mistakeTextView = binding.mistake;
        cardFoImage = binding.cardForImageShow;
        backShow = binding.cancelShow;

        backShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            showStockName.setText(intent.getStringExtra(EXTRA_STOCK_NAME));
            String mistakeLearn = intent.getStringExtra(EXTRA_MISTAKES);
            String learningOutcome = intent.getStringExtra(EXTRA_PROGRESS);

            if (!mistakeLearn.equals("")) {
                showMistake.setVisibility(View.VISIBLE);
                mistakeTextView.setVisibility(View.VISIBLE);
                showMistake.setText(mistakeLearn);
            }

            if (!learningOutcome.equals("")) {
                showProgress.setVisibility(View.VISIBLE);
                progressTextView.setVisibility(View.VISIBLE);
                showProgress.setText(learningOutcome);
            }

            profitLoss = intent.getIntExtra(EXTRA_PROFIT, 0);

            if (profitLoss == 1) {
                showProfit.setVisibility(View.VISIBLE);
                showProfit.setText("PROFIT: " + intent.getFloatExtra(EXTRA_PROFIT_LOSS, 0));
                showProfit.setTextColor(Color.parseColor("#008577"));
            } else if (profitLoss == 2) {
                showProfit.setVisibility(View.VISIBLE);
                showProfit.setText("LOSS: " + intent.getFloatExtra(EXTRA_PROFIT_LOSS, 0));
                showProfit.setTextColor(Color.parseColor("#E4714D"));
            }

            String pathDbImage = intent.getStringExtra(EXTRA_IMAGE_PATH);

            if (pathDbImage != null && !pathDbImage.equals("")) {
                cardFoImage.setVisibility(View.VISIBLE);
                showImage.setVisibility(View.VISIBLE);
                loadImageFromStorage(pathDbImage);
            }

        }
    }

    private void loadImageFromStorage(String path) {

        try {

            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            showImage.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
//
//    private Bitmap getScaledBitmap(Uri selectedImage) throws
//            FileNotFoundException {
//        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
//        sizeOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, sizeOptions);
//
//        int inSampleSize = calculateInSampleSize(sizeOptions);
//
//        sizeOptions.inJustDecodeBounds = false;
//        sizeOptions.inSampleSize = inSampleSize;
//
//        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, sizeOptions);
//    }
//
//    private int calculateInSampleSize(BitmapFactory.Options options) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        System.out.println("Height width is: " + height + " " + width);
//
//        if (height > 800 || width > 800) {
//            // Calculate ratios of height and width to requested one
//            final int heightRatio = Math.round((float) height / (float) 800);
//            final int widthRatio = Math.round((float) width / (float) 800);
//
//            // Choose the smallest ratio as inSampleSize value
//            inSampleSize = Math.min(heightRatio, widthRatio);
//        }
//        return inSampleSize;
//    }
//
//    private void setImageToView(Uri resultUri) {
//        Glide.with(this).load(resultUri).into(showImage);
//    }
//
//    private void launchCropImage(Uri uri) {
//
//        Intent i = CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1920, 1080)
//                .setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getApplicationContext());
//
//        imageSetCrop.launch(i);
//
//    }
//
//    private String saveToInternalStorage(Bitmap bitmapImage){
//        ContextWrapper cw = new ContextWrapper(getApplicationContext());
//        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//        // Create imageDir
//        String previousPath = editImagePathInvisible.getText().toString();
//
//        //delete previous file
//        if(!previousPath.equals("")){
//            File deleteMyPath = new File(previousPath);
//            deleteMyPath.delete();
//        }
//
//        //Create a new File
//        File myPath = new File(directory + "/" + System.currentTimeMillis() + ".png");
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(myPath);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return myPath.getAbsolutePath();
//    }
}