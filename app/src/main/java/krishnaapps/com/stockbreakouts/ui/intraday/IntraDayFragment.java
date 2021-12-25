package krishnaapps.com.stockbreakouts.ui.intraday;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.ui.intraday.stockbreakout.IntraDayBreakoutActivity;

public class IntraDayFragment extends Fragment {

    public IntraDayFragment() {
    }

    private CardView intraDayTrades, indicesTrades;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intraday, container, false);
        intraDayTrades = v.findViewById(R.id.intra_day_stock_breakout);
        indicesTrades  = v.findViewById(R.id.intra_day_indices_breakout);

        intraDayTrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), IntraDayBreakoutActivity.class);
                startActivity(i);
            }
        });

        indicesTrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }


}