package com.example.toma;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button returnBtn;
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new GalleryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAllRecords().observe(this, new Observer<List<Record>>(){
            @Override
            public void onChanged(List<Record> record){
                adapter.updateRecords(record);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == returnBtn){
            HistoryActivity.this.finish();
            overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
            Log.d("HistoryActivity", "Return button clicked.");
        }
    }


    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private final LayoutInflater mInflater;
        private List<Record> mData; // cashed copy

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView textView;

            public ViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.item_image);
                textView = v.findViewById(R.id.item_text);
            }
        }

        GalleryAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recycler_item, viewGroup, false);
            return (new ViewHolder(view));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mData != null) {
                Record current = mData.get(position);
                holder.textView.setText(current.getTime() + "\nFocused for " + current.getDuration() + " min");
                holder.imageView.setImageResource(R.mipmap.number_128px_0 + current.getType());
            } else {
                holder.textView.setText("No data");
            }
        }

        void updateRecords(List<Record> record){
            mData = record;
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            } else return 0;
        }

    }

}
