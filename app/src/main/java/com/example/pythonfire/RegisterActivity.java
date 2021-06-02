package com.example.pythonfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.ListenHashProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리하는 애
    private DatabaseReference mDatebaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mNickname; // 회원가입 입력필드
    private Button mbtnRegister;  // 회원가입 버튼
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatebaseRef = FirebaseDatabase.getInstance().getReference("capstone");

        mNickname = findViewById(R.id.sign_nickname);
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mbtnRegister = findViewById(R.id.btn_register);

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭될때 시작하는곳 (회원가입 처리시작)
                final String strEmail = mEtEmail.getText().toString();
                final String strPwd = mEtPwd.getText().toString();

                //FirebaseAuth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //인증처리가 완료되었을때 회원가입성공이뤄졌을때
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();//현재 유저를 가져온다(회원가입 된 유저
                            if (firebaseUser != null){
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put(firebaseID.documentId, firebaseUser.getUid());
                                userMap.put(firebaseID.nickname, mNickname.getText().toString());
                                userMap.put(firebaseID.email, strEmail);
                                userMap.put(firebaseID.password, strPwd);
                                mStore.collection(firebaseID.firebaseUser).document(firebaseUser.getUid()).set(userMap, SetOptions.merge());
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setName(mNickname.getText().toString());
                                mDatebaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                finish();
                            }

                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}