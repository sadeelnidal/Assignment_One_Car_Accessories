package com.example.caraccessories;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccessoryDetail extends AppCompatActivity {

    private ImageView image;
    private TextView name, price;
    private Spinner color;
    private Button addToCartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_detail);

        initializeViews();

        //data from screan
        String productname = getIntent().getStringExtra("name");
        double num = getIntent().getDoubleExtra("price", 0.0);
        String imageName = getIntent().getStringExtra("imageName");

        name.setText(productname);
        price.setText(String.format("$%.2f", num));

        int imgId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        image.setImageResource(imgId);

        String[] colors = {"Red", "Black"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        color.setAdapter(adapter);


        addToCartBtn.setOnClickListener(v -> {
            double numprice = Double.parseDouble(price.getText().toString().replace("$", ""));
            String productName = name.getText().toString();

            Accessories selectedItem = null;
            for (Accessories item : Shoppingbage.accessoriesList) {
                if (item.getName().equals(productName)) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem != null) {
                if (selectedItem.getStock() > 0) {
                    selectedItem.setStock(selectedItem.getStock() - 1);
                    Cart.items.add(new Accessories(selectedItem.getName(), selectedItem.getPrice(), 1, selectedItem.getImageName()));
                    Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Out of stock!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeViews() {
        image = findViewById(R.id.itemImage);
        name = findViewById(R.id.itemName);
        price= findViewById(R.id.itemPrice);
        color = findViewById(R.id.colorSpinner);
        addToCartBtn = findViewById(R.id.addToCart);
    }

}
