package krishnaapps.com.stockbreakouts.stocks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout.IndicesBreakoutActivity;
import krishnaapps.com.stockbreakouts.stocks.ui.intradaybreakout.IntraDayBreakoutActivity;
import krishnaapps.com.stockbreakouts.stocks.ui.swingBreakout.SwingBreakoutActivity;

public class IntraDayFragment extends Fragment {

    public IntraDayFragment() {
    }

    private CardView intraDayTrades, indicesTrades, swingTrades;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intraday, container, false);
        intraDayTrades = v.findViewById(R.id.intra_day_stock_breakout);
        indicesTrades  = v.findViewById(R.id.intra_day_indices_breakout);
        swingTrades = v.findViewById(R.id.swing_stock_breakout);

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
                startActivity(new Intent(getActivity(), IndicesBreakoutActivity.class));
            }
        });

        swingTrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SwingBreakoutActivity.class));
            }
        });

        return v;
    }


}