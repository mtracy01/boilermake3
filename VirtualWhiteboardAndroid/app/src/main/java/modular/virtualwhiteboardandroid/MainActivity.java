package modular.virtualwhiteboardandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    public static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView =(ListView)findViewById(R.id.list);
        refresh();


    }

    @OnClick(R.id.refresh)
    void refresh(){
        ParseQuery parseQuery = new ParseQuery("Whiteboard");
        parseQuery.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if (e != null) {
                    printErrorMessage(e);
                    return;
                }
                ArrayList<String> names = new ArrayList<>();
                for (Object object : list) {
                    ParseObject parseObject = (ParseObject) object;
                    names.add((String) parseObject.get("boardName"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, (String[]) names.toArray());
                listView.setAdapter(adapter);
            }

            @Override
            public void done(Object o, Throwable throwable) {
                ArrayList<String> names = new ArrayList<>();
                ParseObject parseObject = (ParseObject) o;
                names.add((String) parseObject.get("boardName"));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, (String[]) names.toArray());
                listView.setAdapter(adapter);
            }

            public void printErrorMessage(Exception e) {
                Log.e("MainActivity", e.getMessage());
            }
        });
    }
    @OnClick(R.id.logout)
    void logout(){
        ParseUser.logOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.new_board)
    void nBoard(){
        final EditText editText= new EditText(this);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("New Board");
        b.setMessage("Set board name:");
        b.setView(editText);
        b.setPositiveButton("Create Board", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Check if board by this name already exists
                //TODO: Create board object
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.show();
    }

    @OnClick(R.id.delete)
    void dBoard(){
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("Delete Board");
        b.setMessage("Select a board to delete:");
        b.setView(R.id.list);
        b.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Delete the board and all vectors associated with it
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

}
