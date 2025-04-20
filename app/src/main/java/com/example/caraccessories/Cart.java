package com.example.caraccessories;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    public static List<Accessories> items = new ArrayList<>();
    private ListView listView;
    private Button button;
    private ArrayAdapter<String> adapter;
    private List<String> listviewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.buy);

        listviewData = new ArrayList<>();
        for (Accessories item : items) {
            listviewData.add(String.format("%s\n$%.2f\n", item.getName(), item.getPrice()));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listviewData);
        listView.setAdapter(adapter);


        button.setOnClickListener(v -> {
            for (Accessories item : items) {
                int currentStock = item.getStock();
                item.setStock(currentStock - 1);
            }

            Toast.makeText(this, "Thanks ", Toast.LENGTH_LONG).show();

            items.clear();
            listviewData.clear();
            adapter.notifyDataSetChanged();
        });
    }
}
