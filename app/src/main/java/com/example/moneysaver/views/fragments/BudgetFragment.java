package com.example.moneysaver.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moneysaver.R;
import com.example.moneysaver.controllers.adapters.BdgtRecyclerViewParentAdapter;
import com.example.moneysaver.controllers.appContext.AppContext;
import com.example.moneysaver.models.classes.BudgetItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_budget, container, false);

        ArrayList<BudgetItem> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            list.add(new BudgetItem());
        }

        HashMap<Integer, ArrayList<BudgetItem>> map = new HashMap<>();
        int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= lastDay; i++) {
            ArrayList<BudgetItem> items = new ArrayList<>();

            for (BudgetItem item : list) {
                if (item.getDate().get(Calendar.DAY_OF_MONTH) == i) {
                    items.add(item);
                }
            }

            map.put(i, items);
        }

        Iterator<Map.Entry<Integer,ArrayList<BudgetItem>>> iter = map.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, ArrayList<BudgetItem>> key = iter.next();

            if(map.get(key.getKey()).size() == 0){
                iter.remove();
            }
        }

        RecyclerView recyclerView = v.findViewById(R.id.parent_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.getAppContext());
        BdgtRecyclerViewParentAdapter parentAdapter = new BdgtRecyclerViewParentAdapter(map);
        recyclerView.setAdapter(parentAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return v;
    }
}