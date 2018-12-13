package com.example.anroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.anroid.database.DAO;
import com.example.anroid.database.entities.Note;
import com.example.anroid.gui.NoteItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotesActivity extends AppCompatActivity {
    private boolean isDeleteModeOn = false;
    private static NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button addButton = findViewById(R.id.buttonAddNote);

        ScrollView lv = findViewById(R.id.notes_scrollView);

        addButton.setOnClickListener((listener)->{
            if (NoteItem.isMultiSelected()){
                ArrayList<NoteItem> items = NoteItem.getSelectedItems();
                for (int i = 0; i < items.size(); i++){
                    lv.removeView(items.get(i));

                    DAO.dropNoteById(items.get(i).getGuid(), this);
                    NoteItem.endDeleting();
                }
                items.clear();
                onStart();
            }
            else {
                viewModel.image = null;
                viewModel.text = null;
                viewModel.id = -1;
                Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout notesLayout = findViewById(R.id.notesList);
        notesLayout.removeAllViews();

        Context context = this;

        NoteItem.setDeleteModeFunc((e) ->{
            if (NoteItem.isMultiSelected()){
                addButton.setText(getString(R.string.delete_button_text));
            }
            else {
                addButton.setText(getString(R.string.add_button_text));
            }
        });


        DAO.getAllUserNotes(this, viewModel.name, output -> {
            ArrayList<NoteItem> noteItems = NoteItem.getSelectedItems();
            NoteItem.reloadSelectedItems();

            for(Note note : output){
                boolean flag = false;
                for (int i = 0; i < noteItems.size(); i ++ ){
                    if (noteItems.get(i).getGuid() == note.getId()){
                        flag = true;
                        break;
                    }
                }
                View.OnClickListener ocl = (listener)->{
                    if (!NoteItem.isMultiSelected()) {
                        Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                        viewModel.id = note.getId();;
                        startActivity(intent);
                    }
                    return;
                };

                NoteItem ni = new NoteItem(context, note.getText(), note.getId(), note.getImage(), ocl);
                if (flag){
                    ni.Select(ni);
                }

                notesLayout.addView(ni);
            }
        });
    }
}