package br.udesc.exemplo.nutriapp_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import br.udesc.exemplo.nutriapp_.fragment.HomeFragment;
import br.udesc.exemplo.nutriapp_.fragment.NotificationFragment;
import br.udesc.exemplo.nutriapp_.fragment.ProfileFragment;
import br.udesc.exemplo.nutriapp_.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.navHome:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navSearch:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.navAdd:
                            selectedFragment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        case R.id.navHeart:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.navProfile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    if (selectedFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };
}