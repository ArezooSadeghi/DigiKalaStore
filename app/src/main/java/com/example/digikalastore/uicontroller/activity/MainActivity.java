package com.example.digikalastore.uicontroller.activity;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "productId";

    private ActivityMainBinding mBinding;
    private NavController mNavController;

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
                                mNavController.navigate(R.id.action_categoryFragment_to_homeFragment);
                                return true;
                            case R.id.item_category:
                                mNavController.navigate(R.id.action_homeFragment_to_categoryFragment);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        mNavController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(
                            @NonNull NavController controller,
                            @NonNull NavDestination destination,
                            @Nullable Bundle arguments) {
                        if (destination.getId() == R.id.productDetailFragment) {
                            mBinding.bottomNavigationView.setVisibility(View.GONE);
                        }
                    }
                });

        handleIntent();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public void handleIntent() {
        Intent intent = getIntent();
    }
}
