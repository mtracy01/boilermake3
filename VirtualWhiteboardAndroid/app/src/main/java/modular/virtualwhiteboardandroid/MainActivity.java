package modular.virtualwhiteboardandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        refresh();


    }

    @OnClick(R.id.refresh)
    void refresh(){
        ParseQuery parseQuery = new ParseQuery("Whiteboard");
        parseQuery.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                if(e!=null){
                    printErrorMessage(e);
                    return;
                }
                ArrayList<String> names = new ArrayList<>();
                for(Object object: list){
                    ParseObject parseObject = (ParseObject)object;
                    names.add((String) parseObject.get("boardName"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,(String[])names.toArray());
                listView.setAdapter(adapter);
            }

            @Override
            public void done(Object o, Throwable throwable) {
                ArrayList<String> names = new ArrayList<>();
                ParseObject parseObject = (ParseObject)o;
                names.add((String) parseObject.get("boardName"));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,(String[])names.toArray());
                listView.setAdapter(adapter);
            }
            public void printErrorMessage(Exception e){
                Log.e("MainActivity",e.getMessage());
            }
        });
    }
    @OnClick(R.id.logout)
    void logout(){
        ParseUser.logOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

}
