package com.spm.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spm.ui.Fragments.LocationFragment;
import com.spm.ui.Fragments.SpaceFragment;
import com.spm.ui.Helpers.SortOption;
import com.spm.ui.Interfaces.SortableFragment;
import com.spm.ui.Models.ParkingLocation;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private TextView instructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> bottomNavigationHandler(item.getItemId()));

        instructionTextView = findViewById(R.id.instructionText);

        LocationFragment locationFragment = new LocationFragment();

        // Add locationFragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, locationFragment);
        transaction.commit();

    }

    private boolean bottomNavigationHandler(int id) {
        if (id == R.id.navigation_home) {
            toggleInstructionText(true);
            onBackPressed();
            return true;
        } else if (id == R.id.navigation_favourites) {
            //TODO: Handle favourites action
            return true;
        } else if (id == R.id.navigation_history) {
            //TODO: Handle history action
            return true;
        } else if (id == R.id.navigation_account) {
            //TODO: Handle account action

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment instanceof SortableFragment) {
            if (item.getItemId() == R.id.action_sort_by_name) {
                ((SortableFragment) currentFragment).changeSortOption(SortOption.NAME);
                return true;
            }
            if (item.getItemId() == R.id.action_sort_by_availability) {
                ((SortableFragment) currentFragment).changeSortOption(SortOption.AVAILABLE);
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    public void switchToSpaceFragment(ParkingLocation locationItem) {
        toggleInstructionText(false);
        SpaceFragment spaceFragment = new SpaceFragment();
        Bundle args = new Bundle();
        args.putParcelable("location", locationItem);
        spaceFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, spaceFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Show the up button in the action bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void toggleInstructionText(boolean toggle){
        if (toggle){
            instructionTextView.setVisibility(View.VISIBLE);
        }
        else{
            instructionTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            // Hide the up button in the action bar when we're at the top of the back stack
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            super.onBackPressed();
        }
    }


}
