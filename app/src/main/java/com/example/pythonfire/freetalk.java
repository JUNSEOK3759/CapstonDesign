package com.example.pythonfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythonfire.adapters.postadapter;
import com.example.pythonfire.models.post;

import java.util.ArrayList;
import java.util.List;


public class freetalk extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mPostRecyclerView;

    private postadapter mAdapter;
    private List<post> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mamainin);

        mPostRecyclerView = findViewById(R.id.main_recyclerview);

        mDatas = new ArrayList<>();
//        mDatas.add(new post(null, "title", "contents"));
//        mDatas.add(new post(null, "title", "contents"));
//        mDatas.add(new post(null, "title", "contents"));

        mAdapter = new postadapter(mDatas);
        mPostRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.main_post_edit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, PostActivity.class));
    }
}