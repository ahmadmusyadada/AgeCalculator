package id.ac.polinema.uts;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.ac.polinema.uts.fragments.BirthdayFragment;
import id.ac.polinema.uts.fragments.DetailFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BirthdayFragment birthdayFragment;
    DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        birthdayFragment = new BirthdayFragment();
        detailFragment = new DetailFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, birthdayFragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(String fragment) {
        switch (fragment){
            case "birthdayFragment":
                if (getSupportFragmentManager().findFragmentByTag("BirthdayFragment") != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, birthdayFragment).commit();
                    return true;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, birthdayFragment, "BirthdayFragment").addToBackStack(null).commit();
                    return true;
                }
            case "detailFragment":
                if (getSupportFragmentManager().findFragmentByTag("DetailFragment") != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
                    return true;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment, "DetailFragment").addToBackStack(null).commit();
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.action_calculator:
                fragment = "birthdayFragment";
                break;
            case R.id.action_analysis:
                fragment = "detailFragment";
                break;
        }
        return loadFragment(fragment);
    }
}