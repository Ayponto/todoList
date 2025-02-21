package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<String> list;

    public CustomAdapter(ArrayList<String> list) {
        this.list = list;
    }


    // 가장 중요한 부분 : 아이템뷰를 관리하는 역할
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(com.example.myapplication.R.id.checkbox_Text);
            btnDelete = itemView.findViewById(R.id.Delete_button);
        }
    }

    @NonNull
    @Override
    // 아이템뷰를 생성하는 역할
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // todobox.xml을 가져와서 View 객체로 변환
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todobox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // 리스트에 저장된 데이터를 뷰 객체로 변환된 레이아웃에 넣음
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list.get(position);
        holder.checkBox.setText(item);

        // 체크박스를 눌렀을떄 줄 그어짐
        holder.checkBox.setOnClickListener(v -> {
           if (holder.checkBox.isChecked()){
               holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
           }
           else{
               holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
           }
        });

        // 버튼을 눌렀을때 지우는거
        holder.btnDelete.setOnClickListener(v -> {
            list.remove(position);
            notifyItemRemoved(position);
            // 변경되 위치 반영
            notifyItemRangeChanged(position , list.size());
        });

        // 삭제되면 체크박스 초기화
        holder.checkBox.setPaintFlags(0);
        holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}