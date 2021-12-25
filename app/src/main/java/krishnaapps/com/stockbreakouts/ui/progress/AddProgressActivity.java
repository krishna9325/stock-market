package krishnaapps.com.stockbreakouts.ui.progress;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.databinding.ActivityAddProgressBinding;

import static krishnaapps.com.stockbreakouts.Constants.*;


public class AddProgressActivity extends AppCompatActivity {


    private ActivityAddProgressBinding binding;
    ActivityResultLauncher<Intent> imageSetCrop;
    ActivityResultLauncher<Intent> imagePickGalleryLauncher;

    private ImageView cancel, progressImageView;
    private Button chooseImg, removeImage, save;
    private TextView editImagePathInvisible;
    private int profit = 0;
    private RadioButton rProfit, rLoss;

    private EditText stockName, stockProfitLoss, stockMistake, stockLearning;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cancel = binding.progressCancel;
        chooseImg = binding.chooseImageGallery;
        progressImageView = binding.progressImageView;
        removeImage = binding.removeImageGallery;
        editImagePathInvisible = binding.imagePathInvisible;
        radioGroup = binding.radioGroup;
        rProfit = binding.radioProfit;
        rLoss = binding.radioLoss;

        stockName = binding.progressStockName;
        stockProfitLoss = binding.progressProfitLoss;
        stockMistake = binding.progressMistake;
        stockLearning = binding.progressLearningOutcome;
        save = binding.save;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            stockName.setText(intent.getStringExtra(EXTRA_STOCK_NAME));
            stockMistake.setText(intent.getStringExtra(EXTRA_MISTAKES));
            stockLearning.setText(intent.getStringExtra(EXTRA_PROGRESS));

            profit = intent.getIntExtra(EXTRA_PROFIT, 0);

            if (profit == 1) {
                rProfit.setChecked(true);
                stockProfitLoss.setVisibility(View.VISIBLE);
                stockProfitLoss.setText(String.valueOf(intent.getFloatExtra(EXTRA_PROFIT_LOSS, 0)));
            } else if (profit == 2) {
                rLoss.setChecked(true);
                stockProfitLoss.setVisibility(View.VISIBLE);
                stockProfitLoss.setText(String.valueOf(intent.getFloatExtra(EXTRA_PROFIT_LOSS, 0)));
            }

            String pathDbImage = intent.getStringExtra(EXTRA_IMAGE_PATH);

            if (pathDbImage != null && !pathDbImage.equals("")) {

                editImagePathInvisible.setText(pathDbImage);
                loadImageFromStorage(pathDbImage);
            }

        }

        stockName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    removeEdittextFocus(v);
                }
            }
        });

        stockProfitLoss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    removeEdittextFocus(v);
                }
            }
        });


        stockMistake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    removeEdittextFocus(v);
                }
            }
        });

        stockLearning.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    removeEdittextFocus(v);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_profit) {
                    stockProfitLoss.setVisibility(View.VISIBLE);
                    profit = 1;
                } else if (i == R.id.radio_loss) {
                    stockProfitLoss.setVisibility(View.VISIBLE);
                    profit = 2;
                }
            }

        });

        imageSetCrop = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    CropImage.ActivityResult resultCrop = CropImage.getActivityResult(data);
                    if (result.getResultCode() == RESULT_OK && resultCrop != null) {
                        Uri resultUri = resultCrop.getUri();
                        Bitmap bitmapImg = null;
                        try {
                            bitmapImg = getScaledBitmap(resultUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        editImagePathInvisible.setText(saveToInternalStorage(bitmapImg));
                        setImageToView(resultUri);
                    }
                });

        imagePickGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        Uri imageUri;
                        if (data != null) {
                            imageUri = data.getData();
                            launchCropImage(imageUri);

                        }
                    }
                });

        cancel.setOnClickListener(view -> onBackPressed());

        chooseImg.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            imagePickGalleryLauncher.launch(photoPickerIntent);
        });

        removeImage.setOnClickListener(view -> {
            progressImageView.setImageDrawable(getResources().getDrawable(R.drawable.image_background));
            String imgPathGet = editImagePathInvisible.getText().toString();
            if (!imgPathGet.equals("")) {
                File deleteMyPath = new File(imgPathGet);
                deleteMyPath.delete();
                editImagePathInvisible.setText("");
            }
        });
    }


    private void loadImageFromStorage(String path) {

        try {

            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            progressImageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getScaledBitmap(Uri selectedImage) throws
            FileNotFoundException {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        System.out.println("Height width is: " + height + " " + width);

        if (height > 800 || width > 800) {
            // Calculate ratios of height and width to requested one
            final int heightRatio = Math.round((float) height / (float) 800);
            final int widthRatio = Math.round((float) width / (float) 800);

            // Choose the smallest ratio as inSampleSize value
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    private void setImageToView(Uri resultUri) {
        Glide.with(this).load(resultUri).into(progressImageView);
    }

    private void launchCropImage(Uri uri) {

        Intent i = CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1920, 1080)
                .setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getApplicationContext());

        imageSetCrop.launch(i);

    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String previousPath = editImagePathInvisible.getText().toString();

        //delete previous file
        if (!previousPath.equals("")) {
            File deleteMyPath = new File(previousPath);
            deleteMyPath.delete();
        }

        //Create a new File
        File myPath = new File(directory + "/" + System.currentTimeMillis() + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myPath.getAbsolutePath();
    }

    private void saveNote() {

        String name = stockName.getText().toString();
        ;
        String mistake = stockMistake.getText().toString();
        String learning = stockLearning.getText().toString();
        float profitLoss = 0;
        if ((profit == 1 || profit == 2) && (!stockProfitLoss.getText().toString().equals(""))) {
            profitLoss = Float.parseFloat(stockProfitLoss.getText().toString());
        } else {
            profitLoss = 0;
        }
        String imagePathValue = editImagePathInvisible.getText().toString();

        if (name.equals("") && mistake.equals("") && learning.equals("") && imagePathValue.equals("")) {
            onBackPressed();
        } else {

            Intent data = new Intent();
            data.putExtra(EXTRA_STOCK_NAME, name);
            data.putExtra(EXTRA_MISTAKES, mistake);
            data.putExtra(EXTRA_PROGRESS, learning);
            data.putExtra(EXTRA_PROFIT_LOSS, profitLoss);
            data.putExtra(EXTRA_IMAGE_PATH, imagePathValue);
            data.putExtra(EXTRA_PROFIT, profit);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void removeEdittextFocus(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}