package krishnaapps.com.stockbreakouts.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.SectionsPagerAdapter;
import krishnaapps.com.stockbreakouts.databinding.ActivityMainBinding;
import krishnaapps.com.stockbreakouts.ui.intraday.IntraDayFragment;
import krishnaapps.com.stockbreakouts.ui.positional.PositionalFragment;
import krishnaapps.com.stockbreakouts.ui.progress.ProgressFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,
            R.string.tab_text_3};

    private static final int[] TAB_ICON = new int[]{R.drawable.ic_intraday, R.drawable.ic_positional,
            R.drawable.ic_progress};


    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2 = binding.viewPager;

        sectionsPagerAdapter.addFragment(new IntraDayFragment());
        sectionsPagerAdapter.addFragment(new PositionalFragment());
        sectionsPagerAdapter.addFragment(new ProgressFragment());

        viewPager2.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, viewPager2,
                (tab, position) -> tab.setText(TAB_TITLES[position])).attach();

        for (int i = 0; i < 3; i++) {
            tabs.getTabAt(i).setIcon(TAB_ICON[i]);
        }


    }
}