package krishnaapps.com.stockbreakouts.stocks.ui.indicesbreakout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import krishnaapps.com.stockbreakouts.stocks.adapter.IndicesAdapter;
import krishnaapps.com.stockbreakouts.stocks.model.Indices;

public class IndicesBreakoutActivity extends AppCompatActivity implements IndicesAdapter.IndicesInterface{
    private RecyclerView mIndicesRecyclerView;
    private IndicesAdapter mIndicesAdapter;

    private ProgressBar mProgressCircle;
    private ImageView imgBackBtnIndices;

    private FirebaseFirestore mDatabaseRef;
    private List<Indices> mIndices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indices_breakout);

        imgBackBtnIndices = findViewById(R.id.cancel_indices);
        imgBackBtnIndices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mIndicesRecyclerView = findViewById(R.id.indices_recyclerview);
        mIndicesRecyclerView.setHasFixedSize(true);
        mIndicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.indices_progress_circle);

        mIndices = new ArrayList<>();

        mIndicesAdapter = new IndicesAdapter(IndicesBreakoutActivity.this, mIndices, this);

        mIndicesRecyclerView.setAdapter(mIndicesAdapter);

//        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mDatabaseRef = FirebaseFirestore.getInstance();

        mDatabaseRef.collection("indicesdb").orderBy("indicesKey", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    mIndices.add(dc.getDocument().toObject(Indices.class));
                                }
                                mIndicesAdapter.notifyDataSetChanged();
                            }
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        if(error != null) {
                            Log.e("IndicesBreakout", "Error while uploading data to database" + error);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                    }
                });
    }

    @Override
    public void onClickIndices(int position) {
        Intent i = new Intent(IndicesBreakoutActivity.this, DisplayIndices.class);
        i.putExtra("DisplayIndices", mIndices.get(position));
        startActivity(i);
    }
}