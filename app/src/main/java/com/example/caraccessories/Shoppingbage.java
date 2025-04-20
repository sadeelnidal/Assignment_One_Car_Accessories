package com.example.caraccessories;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shoppingbage extends AppCompatActivity {
    private ListView accessoriesListView;
    private EditText searchtxt;
    private Button search, cart;
    private ArrayAdapter<String> adapter;
    public static List<Accessories> accessoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingbage);

        accessoriesListView = findViewById(R.id.list_item);
        searchtxt = findViewById(R.id.searchInput);
        search = findViewById(R.id.searchButton);
        cart = findViewById(R.id.chart);

        ItemsData();

        List<String> printList = new ArrayList<>();
        for (Accessories item : accessoriesList) {
            String info = String.format("%s\n$%.2f\nStock: %d\n", item.getName(), item.getPrice(), item.getStock());
            printList.add(info);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, printList);
        accessoriesListView.setAdapter(adapter);


        accessoriesListView.setOnItemClickListener((parent, view, position, id) -> {
            Accessories selectedItem = accessoriesList.get(position);

            Intent intent = new Intent(Shoppingbage.this, AccessoryDetail.class);
            intent.putExtra("name", selectedItem.getName());
            intent.putExtra("price", selectedItem.getPrice());
            intent.putExtra("stock", selectedItem.getStock());
            intent.putExtra("imageName", selectedItem.getImageName());
            startActivity(intent);
        });

        search.setOnClickListener(v -> {
            String query = searchtxt.getText().toString().trim().toLowerCase();
            List<String> newList = new ArrayList<>();
            List<Accessories> filterAcc = new ArrayList<>();

            for (Accessories item : accessoriesList) {
                if (item.getName().toLowerCase().contains(query)) {
                    String info = String.format("%s\n$%.2f\nStock: %d\n", item.getName(), item.getPrice(), item.getStock());
                    newList.add(info);
                    filterAcc.add(item);
                }
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newList);
            accessoriesListView.setAdapter(adapter);

        });

        cart.setOnClickListener(v -> {
            Intent intent = new Intent(Shoppingbage.this, Cart.class);
            startActivity(intent);
        });
    }

    private void ItemsData() {
        accessoriesList = new ArrayList<>();
        accessoriesList.add(new Accessories("Car Cover", 25.99, 12, "car_cover"));
        accessoriesList.add(new Accessories("Phone Holder", 8.50, 30, "phone_holder"));
        accessoriesList.add(new Accessories("Air Freshener", 2.99, 100, "air_freshener"));
        accessoriesList.add(new Accessories("Seat Cushion", 15.00, 20, "seat_cushion"));
        accessoriesList.add(new Accessories("Car Lamps", 16.86, 50, "car_lamps"));
    }
    protected void onResume() {
        super.onResume();
        List<String> printList = new ArrayList<>();
        for (Accessories item : accessoriesList) {
            String info = String.format("%s\n$%.2f\nStock: %d\n", item.getName(), item.getPrice(), item.getStock());
            printList.add(info);
        }
        adapter.clear();
        adapter.addAll(printList);
        adapter.notifyDataSetChanged();
    }
}