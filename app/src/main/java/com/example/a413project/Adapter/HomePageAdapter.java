package com.example.a413project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.DataList;
import com.example.a413project.HomeActivity;
import com.example.a413project.R;
import com.example.a413project.ResultActivity;


public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private final Context context;
    private final DataList dataList;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public HomePageAdapter(Context context, DataList dataList) {
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
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.layout, String.valueOf(position));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ResultActivity.class);
                i.putExtra(ResultActivity.SHOW, false);
                i.putExtra(ResultActivity.CLASSIFICATION_CODE, c);
                Log.i(("ACTIVI"), c.toString());
                context.startActivity(i);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.remove(c);
                Log.i("ACTIVI", "postion " + c.toString());
                HomeActivity.adapter.notifyDataSetChanged();
        }
    });

}

    @Override
    public int getItemCount() {
        return dataList.getList().size();
    }

public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView result;
    TextView title;
    Button delete;
    Button detail;
    SwipeRevealLayout layout;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        result = itemView.findViewById(R.id.resultText);
        title = itemView.findViewById(R.id.titleText);
        layout = itemView.findViewById(R.id.swipeLayout);
        delete = itemView.findViewById(R.id.deleteBtnItem);
        detail = itemView.findViewById(R.id.detailBtnItem);
    }
}
}
