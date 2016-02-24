package com.komi.sliderdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Komi on 2016/2/03.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private SparseArrayCompat<Fragment> fragmentArray = new SparseArrayCompat<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Slider");
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> demoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        for (Demo demo : Demo.values()) {
            demoAdapter.add(getString(demo.titleResId));
        }

        listView.setAdapter(demoAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (Demo.values()[position].isActivity()) {
            Class activityClass = null;
            try {
                activityClass = Class.forName(Demo.values()[position].className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (activityClass != null) {
                Intent intent = new Intent(this, activityClass);
                startActivity(intent);
            }

        } else {
            showFragment(Demo.values()[position].className);

        }

    }


    @Override
    public void onBackPressed() {
        if (currentFragment != null && currentFragment.isAdded()) {
            if (currentFragment instanceof FragmentCloseListener) {
                ((FragmentCloseListener) currentFragment).fragmentClosed();
            } else {
                Toast.makeText(this, "onBackPressed fail", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(String fragmentName) {
        int key = fragmentName.hashCode();
        currentFragment = fragmentArray.get(key);

        if (currentFragment == null) {
            currentFragment = Fragment.instantiate(this, fragmentName);
            fragmentArray.put(key, currentFragment);
        }

        if (!currentFragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                openGitHub();
                return true;
            case R.id.menu_share:
                shareApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGitHub() {
        Uri uri = Uri.parse(getString(R.string.github_url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private void shareApp() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.github_url));
        shareIntent.setType("text/plain");


        startActivity(Intent.createChooser(shareIntent, "Share App"));
    }
}
