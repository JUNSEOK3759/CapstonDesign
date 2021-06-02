package com.example.pythonfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythonfire.adapters.postadapter;
import com.example.pythonfire.models.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Frag3 extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mPostRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private postadapter mAdapter;
    private List<post> mDatas;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);
        mPostRecyclerView = view.findViewById(R.id.main_recyclerview);

        mDatas = new ArrayList<>();
//        //mDatas.add(new post(null, "title", "contents"));
//        mDatas.add(new post(null, "title", "contents"));
//        mDatas.add(new post(null, "title", "contents"));



        view.findViewById(R.id.main_post_edit).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(firebaseID.Post)
                .orderBy(firebaseID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()){
                                Map<String, Object> shot = snap.getData();
                                String documentId = String.valueOf(shot.get(firebaseID.documentId));
                                String nickname = String.valueOf(shot.get(firebaseID.nickname));
                                String title = String.valueOf(snap.get(firebaseID.title));
                                String contents = String.valueOf(shot.get(firebaseID.contents));
                                post data = new post(documentId, nickname, title, contents);
                                mDatas.add(data);

                            }
                            mAdapter = new postadapter(mDatas);
                            mPostRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(intent);


    }
}
