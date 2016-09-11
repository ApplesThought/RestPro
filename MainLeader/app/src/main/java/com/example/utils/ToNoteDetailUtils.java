package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.activity.NoteDetailActivity;
import com.example.entity.ReadNote;

import java.util.List;

/**
 * Created by ${hcc} on 2016/06/14.
 */
public class ToNoteDetailUtils {
    public static void toDetail(final Context context,ListView lv, final List<ReadNote> list) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NoteDetailActivity.class);
                String objectId = list.get(position).getObjectId();
                intent.putExtra("objectId",objectId);
                context.startActivity(intent);
            }
        });
    }
}
