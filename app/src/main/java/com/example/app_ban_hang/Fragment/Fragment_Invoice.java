package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_invoice;
import com.example.app_ban_hang.database.CartDao;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Invoice extends Fragment {
    private ArrayList<Integer> cartIDList1;

    public Fragment_Invoice() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__invoice, container, false);
        RecyclerView recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        CartDao cartDao = new CartDao(getContext());
        List<CartItem> cartItemList = new ArrayList<>();
        if (getArguments() != null) {
            ArrayList<Integer> cartIDList = getArguments().getIntegerArrayList("cartIDList");
            cartIDList1 = cartIDList;
            for (int i : cartIDList){
                CartItem cartItem = cartDao.getByCartId(String.valueOf(i));
                Log.d("cartIDList", String.valueOf(i));
                cartItemList.add(cartItem);
            }
        }

        adapter_invoice adapterInvoice = new adapter_invoice(cartItemList, getContext());
        recyclerView3.setAdapter(adapterInvoice);

        float fee = 0.1f  * adapterInvoice.totalAll();
        Log.d("test", String.valueOf(fee));

        TextView tax = view.findViewById(R.id.tax);
        tax.setText(String.valueOf(fee));

        TextView textView9 = view.findViewById(R.id.textView9);
        textView9.setText(String.valueOf(fee));

        TextView totalAll = view.findViewById(R.id.totalAll);
        totalAll.setText(String.valueOf((int)(adapterInvoice.totalAll() + fee*2)));

        Button btn_Order = view.findViewById(R.id.btn_Order);
        btn_Order.setOnClickListener(v -> {
            Fragment_Address fragment = new Fragment_Address();
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("cartIDList", cartIDList1);
            bundle.putFloat("totalAll", adapterInvoice.totalAll() + fee*2);
            fragment.setArguments(bundle);

            // Replace fragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main, fragment) // id của FrameLayout hoặc NavHostFragment
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}