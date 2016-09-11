package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.entity.ReadNote;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class NoteMainLvAdapter extends BaseAdapter{

    private List<ReadNote> list;
    private Context context;
    private LayoutInflater inflater;

    public NoteMainLvAdapter(Context context, List<ReadNote> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_note_main_item,null);

            holder.contentTxt = (TextView) convertView.findViewById(R.id.contentTxt);
            holder.noteTitle = (TextView) convertView.findViewById(R.id.noteTitle);
            holder.noteType = (TextView) convertView.findViewById(R.id.noteType);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.contentTxt.setText(list.get(position).getNoteContent());
        holder.noteTitle.setText(list.get(position).getNoteTitle());
        holder.noteType.setText(list.get(position).getNoteType());

        return convertView;
    }


    static class ViewHolder{
        TextView contentTxt,noteTitle,noteType;
    }
}
