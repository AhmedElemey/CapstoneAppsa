package com.example.ahmed.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ahmed.Chat.LoginActivity;
import com.example.ahmed.R;
import com.example.ahmed.fragments.AboutFragment;
import com.example.ahmed.fragments.DetailFragment;
import com.example.ahmed.fragments.MovieFragment;
import com.example.ahmed.retrofit.Result;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements MovieFragment.callback ,
         NavigationView.OnNavigationItemSelectedListener{

    private boolean mTwoPane = false;

    private DrawerLayout mDrawerLayout ;
    Toolbar toolbar;
    TextView uName , uEmail;
    NavigationView navigationView;

    // onSaveInstanceState
    MovieFragment fragmentSave;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sharedPref;
    public static final String KEY_PREFS = "MY_KEY" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null){

            MovieFragment fragment = new MovieFragment();
            fragment.setListener(this);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment1,fragment);
            transaction.commit();

        }



        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        // onSaveInstanceState
        if(savedInstanceState!=null){
            fragmentSave.positionIndex = savedInstanceState.getInt(BUNDLE_RECYCLER_LAYOUT);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);

        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    // onSaveInstanceState
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(fragmentSave!=null){
            outState.putInt(BUNDLE_RECYCLER_LAYOUT, fragmentSave.positionIndex);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.action_chat){

            Intent ii = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(ii);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Result result) {
        if (mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("result",result);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();


        } else {
            Intent intent = new Intent(this, DetailsActivity.class)
                    .putExtra("result", (Serializable) result);
            startActivity(intent);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String titleItem = (String) item.getTitle();
        toolbar.setTitle(titleItem);

        int itemX = item.getItemId();

        switch (itemX) {

            case R.id.homepage:

                String value = "popular";
                SetSharedPreferences(value);
                CallFragment();
                break;

            case R.id.pop_movie:

                String value1 = "popular";
                SetSharedPreferences(value1);
                CallFragment();
                break;

            case R.id.top_movie:

                String value2 = "top_rated";
                SetSharedPreferences(value2);
                CallFragment();
                break;

            case R.id.up_movie:

                String value3 = "upcoming";
                SetSharedPreferences(value3);
                CallFragment();
                break;

            case R.id.play_movie:

                String value5 = "now_playing";
                SetSharedPreferences(value5);
                CallFragment();
                break;

            case R.id.fav_movie:

                String value4 = "Favourite";
                SetSharedPreferences(value4);
                CallFragment();
                break;



            case R.id.about:
                FragmentManager about = getSupportFragmentManager();
                FragmentTransaction atranc = about.beginTransaction();
                atranc.replace(R.id.fragment1,new AboutFragment(),"rte");
                atranc.commit();
                hideDrawer();
            break;


        }
            return true;
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            hideDrawer();

        else
            super.onBackPressed();
    }

    public void SetSharedPreferences (String type){

        sharedPref = getSharedPreferences(MY_PREFS_NAME ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_PREFS , type);
        editor.commit();

    }

    public void CallFragment (){
        MovieFragment fragment = new MovieFragment();
        fragment.setListener(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment1,fragment);
        transaction.commit();
        hideDrawer();

    }
}
