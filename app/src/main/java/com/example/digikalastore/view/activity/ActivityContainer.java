package com.example.digikalastore.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.digikalastore.R;
import com.example.digikalastore.event.ClickEvent;
import com.example.digikalastore.view.fragment.ExpandableFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ActivityContainer extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.digikalastore.text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new ExpandableFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getClickEvent(ClickEvent clickEvent) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEXT, clickEvent.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ActivityContainer.class);
    }
}