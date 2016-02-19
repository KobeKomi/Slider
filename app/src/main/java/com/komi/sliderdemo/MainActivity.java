package com.komi.sliderdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private SparseArrayCompat<Fragment> fragmentArray = new SparseArrayCompat<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


}
