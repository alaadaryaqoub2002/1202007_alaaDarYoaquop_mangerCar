package edu.cs.birzeit.manger;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarManagementActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText etCarMake, etCarModel, etCarYear, etCarPrice;
    private Button btnAddCar, btnUpdateCar, btnDeleteCar;
    private RecyclerView rvCars;
    private CarAdapter carAdapter;
    private List<Car> carList;
    private long selectedCarId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_management);

        dbHelper = new DBHelper(this);

        etCarMake = findViewById(R.id.etCarMake);
        etCarModel = findViewById(R.id.etCarModel);
        etCarYear = findViewById(R.id.etCarYear);
        etCarPrice = findViewById(R.id.etCarPrice);
        btnAddCar = findViewById(R.id.btnAddCar);
        btnUpdateCar = findViewById(R.id.btnUpdateCar);
        btnDeleteCar = findViewById(R.id.btnDeleteCar);
        rvCars = findViewById(R.id.rvCars);

        carList = new ArrayList<>();
        carAdapter = new CarAdapter(carList, car -> onCarSelected(car.getId()));
        rvCars.setLayoutManager(new LinearLayoutManager(this));
        rvCars.setAdapter(carAdapter);

        btnAddCar.setOnClickListener(v -> addCar());
        btnUpdateCar.setOnClickListener(v -> updateCar());
        btnDeleteCar.setOnClickListener(v -> deleteCar());

        loadCars();
    }

    private void addCar() {
        String make = etCarMake.getText().toString();
        String model = etCarModel.getText().toString();
        int year = Integer.parseInt(etCarYear.getText().toString());
        double price = Double.parseDouble(etCarPrice.getText().toString());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MAKE, make);
        values.put(DBHelper.COLUMN_MODEL, model);
        values.put(DBHelper.COLUMN_YEAR, year);
        values.put(DBHelper.COLUMN_PRICE, price);

        long newRowId = db.insert(DBHelper.TABLE_CARS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Car added", Toast.LENGTH_SHORT).show();
            loadCars();
        } else {
            Toast.makeText(this, "Error adding car", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCar() {
        if (selectedCarId == -1) {
            Toast.makeText(this, "Select a car to update", Toast.LENGTH_SHORT).show();
            return;
        }

        String make = etCarMake.getText().toString();
        String model = etCarModel.getText().toString();
        int year = Integer.parseInt(etCarYear.getText().toString());
        double price = Double.parseDouble(etCarPrice.getText().toString());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MAKE, make);
        values.put(DBHelper.COLUMN_MODEL, model);
        values.put(DBHelper.COLUMN_YEAR, year);
        values.put(DBHelper.COLUMN_PRICE, price);

        int count = db.update(DBHelper.TABLE_CARS, values, DBHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(selectedCarId)});
        if (count > 0) {
            Toast.makeText(this, "Car updated", Toast.LENGTH_SHORT).show();
            loadCars();
        } else {
            Toast.makeText(this, "Error updating car", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCar() {
        if (selectedCarId == -1) {
            Toast.makeText(this, "Select a car to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(DBHelper.TABLE_CARS, DBHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(selectedCarId)});
        if (count > 0) {
            Toast.makeText(this, "Car deleted", Toast.LENGTH_SHORT).show();
            loadCars();
        } else {
            Toast.makeText(this, "Error deleting car", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCars() {
        carList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_CARS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            String make = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MAKE));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MODEL));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_YEAR));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));

            Car car = new Car(id, make, model, year, price);
            carList.add(car);
        }

        cursor.close();
        carAdapter.notifyDataSetChanged();
    }


    public void onCarClick(int position) {
        Car clickedCar = carList.get(position);
        selectedCarId = clickedCar.getId();

        etCarMake.setText(clickedCar.getMake());
        etCarModel.setText(clickedCar.getModel());
        etCarYear.setText(String.valueOf(clickedCar.getYear()));
        etCarPrice.setText(String.valueOf(clickedCar.getPrice()));
    }

    private void onCarSelected(long carId) {
        selectedCarId = carId;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_CARS,
                null,
                DBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            etCarMake.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MAKE)));
            etCarModel.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MODEL)));
            etCarYear.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_YEAR))));
            etCarPrice.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE))));
            cursor.close();
        }
    }
}

