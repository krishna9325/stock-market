package krishnaapps.com.stockbreakouts.stocks.ui.swingBreakout;

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
import krishnaapps.com.stockbreakouts.stocks.adapter.SwingAdapter;
import krishnaapps.com.stockbreakouts.stocks.model.Swing;
import krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout.DisplayIndices;
import krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout.IndicesBreakoutActivity;

public class SwingBreakoutActivity extends AppCompatActivity implements SwingAdapter.SwingInterface {
    private RecyclerView mSwingRecyclerView;
    private SwingAdapter mSwingAdapter;

    private ProgressBar mSwingProgressCircle;

    private FirebaseFirestore mSwingDatabaseRef;
    private List<Swing> mSwing;

    private ImageView imgBacktnSwing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swing_breakout);

        mSwingRecyclerView = findViewById(R.id.swing_recyclerview);
        mSwingRecyclerView.setHasFixedSize(true);
        mSwingRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        imgBacktnSwing = findViewById(R.id.cancel_swing);
        imgBacktnSwing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        mSwingProgressCircle = findViewById(R.id.swing_progress_circle);

        mSwing = new ArrayList<>();

        mSwingAdapter = new SwingAdapter(SwingBreakoutActivity.this, mSwing, this);

        mSwingRecyclerView.setAdapter(mSwingAdapter);

//        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mSwingDatabaseRef = FirebaseFirestore.getInstance();

        mSwingDatabaseRef.collection("swingdb").orderBy("swingKey", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    mSwing.add(dc.getDocument().toObject(Swing.class));
                                }
                                mSwingAdapter.notifyDataSetChanged();
                            }
                            mSwingProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        if(error != null) {
                            Log.e("SwingBreakoutActivity", "Error while uploading data to database" + error);
                            mSwingProgressCircle.setVisibility(View.INVISIBLE);
                        }

                    }
                });
    }

    @Override
    public void onClickSwing(int position) {
        Intent i = new Intent(SwingBreakoutActivity.this, DisplaySwing.class);
        i.putExtra("DisplaySwing", mSwing.get(position));
        startActivity(i);
    }
}