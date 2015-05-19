package com.wapmadrid.activities;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.wapmadrid.R;

public class NewGroupActivity extends Activity {

    private LinearLayout lyNewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        lyNewGroup = (LinearLayout) findViewById(R.id.lyNewGroup);
        setWidthToParent();
    }

    private void setWidthToParent() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        getWindow().setLayout(width, lyNewGroup.getLayoutParams().height);
    }
}
