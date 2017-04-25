package com.example.android.inventroytrack;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.android.inventroytrack.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG=MainActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    public void clickOnViewItem(long id){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("itemid",id);
        startActivity(intent);
    }

    public void clickOnSale(long id,int quantity){
        dbHelper.sellOneItem(id,quantity);
        adapter.swapCursor(dbHelper.readStock());
    }
}
