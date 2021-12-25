package krishnaapps.com.stockbreakouts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();

    public SectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return lstFragment.size();
    }

    public void addFragment(Fragment fragment) {
        lstFragment.add(fragment);
    }

}