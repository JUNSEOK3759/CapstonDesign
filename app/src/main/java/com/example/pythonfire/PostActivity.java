package com.example.pythonfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseDatabase mBase = FirebaseDatabase.getInstance();
    private EditText mTitle, mContents;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_title_contents);

        findViewById(R.id.post_save_button).setOnClickListener(this);

        if (mAuth.getCurrentUser() != null) {

            mStore.collection(firebaseID.firebaseUser).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null){
                                nickname = (String) task.getResult().getData().get(firebaseID.nickname);
                            }
                        }
                    });

        }
    }

    @Override
    public void onClick(View view) {
        if(mAuth.getCurrentUser() != null){
            String postId = mStore.collection(firebaseID.Post).document().getId();
            Map<String, Object> data = new HashMap<>();
            data.put(firebaseID.documentId, mAuth.getCurrentUser().getUid());
            data.put(firebaseID.nickname, nickname);
            data.put(firebaseID.title, mTitle.getText().toString());
            data.put(firebaseID.contents, mContents.getText().toString());
            data.put(firebaseID.timestamp, FieldValue.serverTimestamp());
            mStore.collection(firebaseID.Post).document(postId).set(data, SetOptions.merge());
            finish();


        }

    }
}