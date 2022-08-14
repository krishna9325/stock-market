package krishnaapps.com.stockbreakouts.stocks.ui.intradaybreakout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.stocks.adapter.IntraDayAdapter;
import krishnaapps.com.stockbreakouts.stocks.model.IntraDay;
import krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout.DisplayIndices;
import krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout.IndicesBreakoutActivity;


public class IntraDayBreakoutActivity extends AppCompatActivity implements IntraDayAdapter.IntradayInterface {
    private RecyclerView mRecyclerView;
    private IntraDayAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseFirestore mDatabaseRef;
    private List<IntraDay> mIntraDays;

    private ImageView imgBackBtnIntraDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intraday_breakout);

        mRecyclerView = findViewById(R.id.intra_day_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        imgBackBtnIntraDay = findViewById(R.id.cancel_intra_day);
        imgBackBtnIntraDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        mProgressCircle = findViewById(R.id.progress_circle);

        mIntraDays = new ArrayList<>();

        mAdapter = new IntraDayAdapter(IntraDayBreakoutActivity.this, mIntraDays, this);

        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mDatabaseRef = FirebaseFirestore.getInstance();

        mDatabaseRef.collection("uploads").orderBy("key", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            mIntraDays.add(dc.getDocument().toObject(IntraDay.class));
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                if(error != null) {
                    Log.e("IntraDayBreakout", "Error while uploading data to database" + error);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    public void onClickIntraday(int position) {
        Intent i = new Intent(IntraDayBreakoutActivity.this, DisplayIntraday.class);
        i.putExtra("DisplayIntraDay", mIntraDays.get(position));
        startActivity(i);
    }
}