package com.example.a413project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.DataList;
import com.example.a413project.R;
import com.example.a413project.ResultActivity;

import javax.xml.transform.Result;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private Context context;
    private DataList dataList;

    public HomePageAdapter(Context context, DataList dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(
                R.layout.listview_item,
                parent,
                false
        );
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Classification c = dataList.getList().get(position);
        holder.title.setText(position + 1 + " The Label is ");
        holder.result.setText(c.getLabel());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ResultActivity.class);
                i.putExtra(ResultActivity.SHOW, false);
                i.putExtra(ResultActivity.CLASSIFICATION_CODE, c);
                Log.i(("ACTIVI"), c.toString());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView result;
        TextView title;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            result = itemView.findViewById(R.id.resultText);
            title = itemView.findViewById(R.id.titleText);
            layout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
