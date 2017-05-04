package com.example.android.inventroytrack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.android.inventroytrack.data.InventoryDbHelper;
import com.example.android.inventroytrack.data.StockItem;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new InventoryDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new StockCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                if (currentFirstVisibleItem > lastVisibleItem) {
                    fab.show();
                } else if (currentFirstVisibleItem < lastVisibleItem) {
                    fab.hide();
                }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add data for demo purposes
     */
    private void addDummyData() {

        StockItem dairyMilk = new StockItem(
                "DairyMilk",
                "₹ 50 ",
                24,
                "anand ",
                "+49 000 000 0000",
                "anand@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/dairymilk");
        dbHelper.insertItem(dairyMilk);

        StockItem cherries = new StockItem(
                "Cherries",
                "₹ 11 ",
                74,
                "anand",
                "+49 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/cherry");
        dbHelper.insertItem(cherries);

        StockItem cola = new StockItem(
                "Cola",
                "₹ 13 ",
                44,
                "anand",
                "9 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/cola");
        dbHelper.insertItem(cola);

        StockItem fivestar = new StockItem(
                "5 star",
                "₹ 20 ",
                34,
                "Haribo GmbH",
                "4 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/fivestar");
        dbHelper.insertItem(fivestar);

        StockItem smurfs = new StockItem(
                "Smurfs",
                "₹ 12 ",
                26,
                "shivam",
                "00 000 0000",
                "shivam@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/smurfs");
        dbHelper.insertItem(smurfs);

        StockItem pulse = new StockItem(
                "Pulse",
                "₹ 9 ",
                54,
                "anand.",
                "+7848485959",
                "anand.com",
                "android.resource://com.example.android.inventroytrack/drawable/pulse");
        dbHelper.insertItem(pulse);




        StockItem lolipopStrawberry = new StockItem(
                "Lolipop strawberry",
                "₹ 120 ",
                62,
                "Fiesta S.A.",
                "+34 000 000 0000",
                "mickey",
                "android.resource://com.example.android.inventroytrack/drawable/lolipop");
        dbHelper.insertItem(lolipopStrawberry);

        StockItem darkchocolate = new StockItem(
                "Dark Chocolate",
                "₹ 130 ",
                22,
                "babber",
                "3000 000 0000",
                "babber@sweet.com",
                "android.resource://com.example.android.inventroytrack/drawable/darkchocolate");
        dbHelper.insertItem(darkchocolate);
    }
}