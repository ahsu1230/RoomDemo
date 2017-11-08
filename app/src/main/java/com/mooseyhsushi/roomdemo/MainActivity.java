package com.mooseyhsushi.roomdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mooseyhsushi.roomdemo.db.MyDatabase;
import com.mooseyhsushi.roomdemo.db.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask task = new SomeTask(this);
        task.execute();
    }

    public class SomeTask extends AsyncTask<Object, Void, Void> {
        final Context mContext;

        private SomeTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Object... objs) {
            MyDatabase db = MyDatabase.get(mContext);
            long now = System.currentTimeMillis();

            User user = new User();
            user.firstName = "Aaron";
            user.lastName = "Hsu";
            user.createdAt = now;
            user.updatedAt = now;
            user.age = 26;
            db.getUserDao().insertOne(user);

            List<User> users = db.getUserDao().getAll();
            String text = "Num users: " + users.size();
            Log.d("MainActivity", text);
//            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();

            return null;
        }
    }
}
