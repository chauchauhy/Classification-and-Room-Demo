package com.example.a413project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.DataList;
import com.example.a413project.R;
import com.example.a413project.ResultActivity;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.transform.Result;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
    public void recyclerViewAction(RecyclerView recyclerView, HomePageAdapter adapter){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // in this case, nothing to do
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        if (position < DataList.list.size()) {
                            DataList.list.remove(position);
                            Log.i("ACTIVI", "postion " + String.valueOf(position));
                            Classification c = dataList.getList().get(position);
                            dataList.remove(c);
                            adapter.notifyItemRemoved(position);
                        }else{
                            Toast.makeText(context, context.getString(R.string.alertClear), Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(context,android.R.color.holo_red_dark))
                        .addActionIcon(R.drawable.ic_baseline_delete_outline_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
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
