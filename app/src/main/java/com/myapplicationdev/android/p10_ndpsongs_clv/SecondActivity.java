package com.myapplicationdev.android.p10_ndpsongs_clv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv;
    ArrayList<Song> songList;
	//ArrayAdapter adapter;
    CustomAdapter adapter;
	String moduleCode;
	int requestCode = 9;
    Button btn5Stars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

		lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);

		DBHelper dbh = new DBHelper(this);
        songList = dbh.getAllSongs();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, songList);
		lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("song", songList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == this.requestCode && resultCode == RESULT_OK){
			DBHelper dbh = new DBHelper(this);
            songList.clear();
            songList.addAll(dbh.getAllSongs());
            dbh.close();
            adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
