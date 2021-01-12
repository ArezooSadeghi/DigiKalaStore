package com.example.digikalastore.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ActivityMainBinding;
import com.example.digikalastore.event.AddEvent;
import com.example.digikalastore.event.DeleteEvent;
import com.example.digikalastore.event.Event;
import com.example.digikalastore.event.RemoveEvent;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_PRODUCT_ID = "com.example.digikalastore.productId";
    public static final int REQUEST_ERROR = 0;

    private ActivityMainBinding mBinding;
    private NavController mNavController;
    private int mNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavigationView, mNavController);

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                Navigation.findNavController(
                                        MainActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.homeFragment);
                                break;
                            case R.id.item_category:
                                Navigation.findNavController(
                                        MainActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.categoryFragment);
                                break;
                            case R.id.item_cart:
                                Navigation.findNavController(
                                        MainActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.cartFragment);
                                break;
                        }
                        item.setChecked(true);
                        return true;
                    }
                });

        mNavController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(
                            @NonNull NavController controller,
                            @NonNull NavDestination destination,
                            @Nullable Bundle arguments) {
                        if (destination.getId() == R.id.createAccountFragment ||
                                destination.getId() == R.id.registerFragment ||
                                destination.getId() == R.id.addressFragment ||
                                destination.getId() == R.id.getLocationFragment) {
                            mBinding.bottomNavigationView.setVisibility(View.GONE);
                        } else {
                            mBinding.bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        handleIntent();
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
    public void getNumber(Event event) {
        setBadge(++mNumber);
    }

    @Subscribe
    public void getNumberWithAddEvent(AddEvent addEvent) {
        setBadge(++mNumber);
    }

    @Subscribe
    public void getNumberWithRemoveEvent(RemoveEvent removeEvent) {
        setBadge(--mNumber);
    }

    @Subscribe
    public void getNumberWithDeleteEvent(DeleteEvent deleteEvent) {
        --mNumber;
        BadgeDrawable badgeDrawable = mBinding.bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        badgeDrawable.setVisible(false);
    }

    public void setBadge(int number) {
        BadgeDrawable badgeDrawable = mBinding.bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        badgeDrawable.setNumber(number);
        badgeDrawable.setVisible(true);
    }

    public void handleIntent() {
        Intent intent = getIntent();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
