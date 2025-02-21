package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 앱의 메인 화면을 담당하는 클래스
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<String> list;


    // 앱이 실행될때 실행되는 초기 설정들이 이 메서드 안에서 함
    // ex) 화면에 어떤 레이아웃을 표시할지, 초기화할 변수들을 설정함
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //----------------------------기본 설정-----------------------------------------------

        recyclerView = findViewById(R.id.recycleview);

        // 리사이클뷰을 사용할때 배치 방식을을 정해야함
        // new LinearLayoutManager(this) 기본인 세로로 정렬하는 역할
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        // 리스트 데이터를 어댑터에 전달
        adapter = new CustomAdapter(list);

        // 리사이클 뷰 어댑터 설정 = 화면에 표시
        recyclerView.setAdapter(adapter);

        // 버튼 클릭 리스너 설정
         // Xml에 있는 버튼을 특정짓는 설정 => id을 가지고 와서 변수에 저장
        Button buttonForDilog = findViewById(R.id.button_Dilog);

         // 버튼을 클릭했을 때 실행될 동작을 정의하는 리스너 => 버튼변수.setOnClickListener
        buttonForDilog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });


    }


    public void showInputDialog() {
        // AlertDialog는 메세지를 입력 받을때 주로 사용하는 다이얼로그임 , .Builder는 이 다이얼로그를 만들기 위한 클래스임
        //로직
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //다자인
        builder.setTitle("Todo");

        //시스템 상 정의
        // 사용자에세 입력받기 위한 입력 필드 생성
       final EditText input = new EditText(this);
       //거기에 힌트를 넣음
        input.setHint("여기에 입력하세요");

        // 우리 눈에 보이도록 정의 => 그리고 우리가 쓴 걸 EditText로 변환
        // 다이얼로그의 내용으로 EditText를 설정
        // AlertDialog는 기본적으로 제목과 메세자만 표시할 수 있지만 setView를 사용하면 다이얼로 그 안에 사용자의 정의 뷰를 넣을 수 있음
        // 한마디로 시트템 상의로 정의 내린 객체를 시각적으로 보여줄 수 있게 하는게 .setView이다.
        builder.setView(input);


        // Positive 버튼 설정
       builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               //getText로 Editable 객체를 반환 => toString으로 String으로 변환
               //무조건 가지고 오는 명령어 필요
               String userInput = input.getText().toString();
               // 그니까 Toast.makeText의 context는 어디에 띄울 것인지를 의미함 그래서 MainActivity.this 현제 화면을 구성하는
               // MainActivity.this => 여기 메인 화면에 넣어줘 ===> 이런 의미가 되는거임

               if(!userInput.isEmpty()) {
                   list.add(userInput);

                   // 약간 flutter에서 setState와 비슷한거임 화면 갱신하는거
                   adapter.notifyDataSetChanged();
                   Toast.makeText(MainActivity.this, "입력한 내용: " + userInput, Toast.LENGTH_SHORT).show();

               }

           }
       });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // 다이얼로그 닫기
            }
        });

        builder.show();
    }

}


