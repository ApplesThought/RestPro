package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.R;
import com.example.ccinterface.MyItemClickListener;
import com.example.ccinterface.MyItemLongClickListener;
import com.example.entity.ReadNote;

import java.util.List;


/**
 * Created by ${hcc} on 2016/04/08.
 */
public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteHolder> {

    private List<ReadNote> list;

    private LayoutInflater inflater;

    private Context context;


    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public NoteRecyclerAdapter(Context context, List<ReadNote> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    public void updateData(List<ReadNote> newItems) {
        if (newItems != null) {
            list = newItems;
            notifyItemChanged(list.size());
        }
    }

    public void setOnItemClickListner(MyItemClickListener listener){
        this.myItemClickListener = listener;
    }



    @Override
    public NoteHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = inflater.inflate(R.layout.lv_note_item,null);
        NoteHolder holder = new NoteHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final NoteHolder holder, final int position) {

//        holder.itemView.setTag(list.get(position));

        holder.contentTxt.setText(list.get(position).getNoteContent());
        holder.noteTitle.setText(list.get(position).getNoteTitle());
        holder.noteType.setText(list.get(position).getNoteType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, holder.getPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    class NoteHolder extends RecyclerView.ViewHolder{

        TextView contentTxt,noteTitle,noteType;

        NoteHolder(View itemView) {
            super(itemView);
            contentTxt = (TextView) itemView.findViewById(R.id.contentTxt);
            noteTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            noteType = (TextView) itemView.findViewById(R.id.noteType);
        }

    }

}
