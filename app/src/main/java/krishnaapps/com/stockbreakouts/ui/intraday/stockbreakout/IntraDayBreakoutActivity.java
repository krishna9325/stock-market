package krishnaapps.com.stockbreakouts.ui.intraday.stockbreakout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class IntraDayBreakoutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseFirestore mDatabaseRef;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intra_day_breakout);

        mRecyclerView = findViewById(R.id.intra_day_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(IntraDayBreakoutActivity.this, mUploads);

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
                            mUploads.add(dc.getDocument().toObject(Upload.class));
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
}