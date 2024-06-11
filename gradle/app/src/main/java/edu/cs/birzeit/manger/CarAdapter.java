package edu.cs.birzeit.manger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;
    private OnCarSelectedListener listener;

    public CarAdapter(List<Car> carList, OnCarSelectedListener listener) {
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.tvMakeModel.setText(car.getMake() + " " + car.getModel());
        holder.tvYearPrice.setText("Year: " + car.getYear() + ", Price: $" + car.getPrice());

        holder.itemView.setOnClickListener(v -> listener.onCarSelected(car));
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tvMakeModel;
        TextView tvYearPrice;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMakeModel = itemView.findViewById(android.R.id.text1);
            tvYearPrice = itemView.findViewById(android.R.id.text2);
        }
    }

    public interface OnCarSelectedListener {
        void onCarSelected(Car car);
    }
}

