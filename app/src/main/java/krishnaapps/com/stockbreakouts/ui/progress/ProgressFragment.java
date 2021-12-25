package krishnaapps.com.stockbreakouts.ui.progress;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import krishnaapps.com.stockbreakouts.R;
import krishnaapps.com.stockbreakouts.ui.progress.entity.Note;

import static krishnaapps.com.stockbreakouts.Constants.*;

public class ProgressFragment extends Fragment {

    public ProgressFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton floatingActionButton;
    private View view;
    private ProgressViewModel progressViewModel;
    private RecyclerView recyclerView;

    private ActivityResultLauncher<Intent> sendDataForResult, updateInputResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_progress, container, false);

        floatingActionButton = view.findViewById(R.id.add_fab);
        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_progress);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        progressViewModel.getAllNotes().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onCVItemClick(Note note) {

                Intent intent = new Intent(getActivity(), ShowProgress.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_STOCK_NAME, note.getStockName());
                intent.putExtra(EXTRA_PROFIT_LOSS, note.getProfitLoss());
                intent.putExtra(EXTRA_MISTAKES, note.getAnyMistake());
                intent.putExtra(EXTRA_PROGRESS, note.getProgress());
                intent.putExtra(EXTRA_IMAGE_PATH, note.getImagePath());
                intent.putExtra(EXTRA_PROFIT, note.getProfit());
                startActivity(intent);
            }

            @Override
            public void onEditCVClick(Note note) {
                Intent intent = new Intent(getActivity(), AddProgressActivity.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_STOCK_NAME, note.getStockName());
                intent.putExtra(EXTRA_PROFIT_LOSS, note.getProfitLoss());
                intent.putExtra(EXTRA_MISTAKES, note.getAnyMistake());
                intent.putExtra(EXTRA_PROGRESS, note.getProgress());
                intent.putExtra(EXTRA_IMAGE_PATH, note.getImagePath());
                intent.putExtra(EXTRA_PROFIT, note.getProfit());
                updateInputResult.launch(intent);
            }

            @Override
            public void onDeleteNote(Note note) {

                Snackbar.make(getActivity().findViewById(android.R.id.content), "Do you want to delete?", Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                progressViewModel.delete(note);
                                Toast.makeText(getActivity(), "Deleted!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });


        sendDataForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data != null) {
                            String stockName = data.getStringExtra(EXTRA_STOCK_NAME);
                            float stockProfitLoss = data.getFloatExtra(EXTRA_PROFIT_LOSS, 0);
                            String stockProgress = data.getStringExtra(EXTRA_PROGRESS);
                            String stockMistake = data.getStringExtra(EXTRA_MISTAKES);
                            String imagePath = data.getStringExtra(EXTRA_IMAGE_PATH);
                            int profit = data.getIntExtra(EXTRA_PROFIT, 0);
                            Note note = new Note(stockProfitLoss, stockName, stockMistake, stockProgress, imagePath, profit);
                            progressViewModel.insert(note);
                        }
                    }
                });

        updateInputResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data != null) {
                            int id = data.getIntExtra(EXTRA_ID, -1);
                            if (id == -1) {
                                Toast.makeText(getActivity(), "Unable to update record", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String stockName = data.getStringExtra(EXTRA_STOCK_NAME);
                            float stockProfitLoss = data.getFloatExtra(EXTRA_PROFIT_LOSS, 0);
                            String stockProgress = data.getStringExtra(EXTRA_PROGRESS);
                            String stockMistake = data.getStringExtra(EXTRA_MISTAKES);
                            String imagePath = data.getStringExtra(EXTRA_IMAGE_PATH);
                            int profit = data.getIntExtra(EXTRA_PROFIT, 0);
                            Note note = new Note(stockProfitLoss, stockName, stockMistake, stockProgress, imagePath, profit);
                            note.setId(id);
                            progressViewModel.update(note);
                        }
                    }
                });

        floatingActionButton.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), AddProgressActivity.class);
                sendDataForResult.launch(intent);
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Permission required", Snackbar.LENGTH_LONG)
                        .setAction("Give Permission", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
        return view;
    }
}