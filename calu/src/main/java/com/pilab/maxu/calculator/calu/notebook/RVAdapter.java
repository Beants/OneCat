package com.pilab.maxu.calculator.calu.notebook;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pilab.maxu.calculator.calu.R;

import java.util.List;

/**
 * Created by andersen on 2018/9/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NoteViewHolder> {
    List<NoteBookActivity.Note> notes;

    RVAdapter(List<NoteBookActivity.Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_card, viewGroup, false);
        NoteViewHolder pvh = new NoteViewHolder(v);
        return pvh;
    }

    @Override

    public void onBindViewHolder(final NoteViewHolder noteViewHolder, @SuppressLint("RecyclerView") final int i) {
        noteViewHolder.noteTime.setText(notes.get(i).time);
        noteViewHolder.noteTitle.setText(notes.get(i).title);
        noteViewHolder.noteContent.setText(notes.get(i).content);
        noteViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("a", "onClick: aaaaaaaaaaa111234567890987654321234567890987654321234567890");
                onItemClickListener.onItemClick(noteViewHolder.itemView, i);
                Log.i("a", "onClick: aaaaaaaaaaa111234567890987654321234567890987654321234567890");
            }
        });
        noteViewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i("a", "onClick: 长摁");
                onItemClickListener.onItemLongClick(noteViewHolder.itemView, i);

                return false;
            }
        });
    }

    void setOnItemClickListener(RVAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 自定义监听回调，RecyclerView 的 单击和长按事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView noteTitle;
        TextView noteContent;
        TextView noteTime;

        NoteViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_note);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteTime = (TextView) itemView.findViewById(R.id.note_time);
            noteContent = (TextView) itemView.findViewById(R.id.note_content);
        }
    }

}