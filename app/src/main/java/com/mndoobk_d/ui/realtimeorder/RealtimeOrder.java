package com.mndoobk_d.ui.realtimeorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mndoobk_d.R;
import com.mndoobk_d.adapter.Adapter;
import com.mndoobk_d.api.RetrofitCon;
import com.mndoobk_d.databinding.FragmentNotificationsBinding;
import com.mndoobk_d.db.DatabaseHandler;
import com.mndoobk_d.model.DataResponse;
import com.mndoobk_d.model.OrderDataModel;
import com.mndoobk_d.model.UserModel;
import com.mndoobk_d.ui.allorder.Allorder;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealtimeOrder extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    Adapter<OrderDataModel> OrderDataModelAdapter;
    ArrayList<OrderDataModel> OrderDataModelArrayList;
    RecyclerView recyclerView;
    int id;
    String name;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        recyclerView = root.findViewById(R.id.neworder_rcvl);
        db = new DatabaseHandler(getContext());
        user= db.getUser();
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        OrderDataModelArrayList = new ArrayList<>();

        rcvl(OrderDataModelArrayList);
        OrderDataModelArrayList.clear();

        getData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void getData(){
        progress.show();
        databaseReference.child("orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue() !=null){
                    OrderDataModelArrayList.add(snapshot.getValue(OrderDataModel.class));
                    OrderDataModelAdapter.notifyDataSetChanged();
//                    Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() !=null){
                    for (int i = 0; i < OrderDataModelArrayList.size();i++){
                        if (OrderDataModelArrayList.get(i).getId() == Integer.parseInt(snapshot.getKey())){
                            removeAt(i);
                        }
                    }
//                    Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progress.dismiss();

    }


    public void rcvl(ArrayList<OrderDataModel> arrayList) {
        OrderDataModelAdapter = new Adapter<OrderDataModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.myorder_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final OrderDataModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.id.setText(String.valueOf(val.getId()));
//                adapterHolder.storename.setText(val.());
                adapterHolder.distance.setText(val.getDistance());
                adapterHolder.from.setText(val.getFrom());
                adapterHolder.to.setText(val.getTo());
                adapterHolder.price.setText(String.valueOf(val.getPrice()) );
                adapterHolder.status.setText(val.getStatus());
                adapterHolder.number.setText(String.valueOf(val.getPhone()));
                String inputPattern = "yyyy-MM-dd HH:mm";
                String outputPattern = "dd-MMM-yyyy h:mm a";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


                try {
                    if (val.getTime() != null && !val.getTime().isEmpty()) {
                        String newstdate = null;
                        if (val.getTime().contains("T")){
                             newstdate = val.getTime().replace("T"," ");
                        }else {
                            newstdate = val.getTime();
                        }

                        Date time = inputFormat.parse(newstdate);

                        assert time != null;
                        adapterHolder.time.setText(outputFormat.format(time));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        adapterHolder.btn.setText("يمكنك استلام الطلب بعد " + (millisUntilFinished/1000));

                        adapterHolder.btn.setEnabled(false);
                    }

                    public void onFinish() {
                        adapterHolder.btn.setText("استلام");
                        adapterHolder.btn.setEnabled(true);
                    }
                }.start();

                adapterHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progress.show();

                        new RetrofitCon(getContext()).getService().receive("Bearer "+user.getAccessToken(),val.getId()).enqueue(new Callback<DataResponse<OrderDataModel>>() {
                            @Override
                            public void onResponse(Call<DataResponse<OrderDataModel>> call, Response<DataResponse<OrderDataModel>> response) {
                                if (response.isSuccessful()){
                                    if (!response.body().isSuccess()){

                                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                                    }else {
                                        db.addoOrder(val);
                                        openFragment(Allorder.newInstance());                                    }
                                }else {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                                progress.dismiss();

                            }

                            @Override
                            public void onFailure(Call<DataResponse<OrderDataModel>> call, Throwable t) {
                                if (t.getLocalizedMessage() != null) {
                                    Log.e("ErrorFailure", t.getLocalizedMessage());
                                    progress.dismiss();

                                }
                            }
                        });
                    }
                });




            }
        };
        recyclerView.setAdapter(OrderDataModelAdapter);

    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public long printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

//        System.out.println("startDate : " + startDate);
//        System.out.println("endDate : "+ endDate);
//        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
//
//        long elapsedDays = different / daysInMilli;
//        different = different % daysInMilli;
//
//        long elapsedHours = different / hoursInMilli;
//        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

//        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedMinutes;

    }
    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView id,from,to,status,price,distance,storename,number,time;
        Button btn;
        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.myorder_btn);

            id = itemView.findViewById(R.id.myorder_orderid);
            from = itemView.findViewById(R.id.myorder_from);
            to = itemView.findViewById(R.id.myorder_to);
            status = itemView.findViewById(R.id.myorder_status);
            price = itemView.findViewById(R.id.myorder_price);
            distance = itemView.findViewById(R.id.myorder_distance);
            storename = itemView.findViewById(R.id.myorder_storname);
            number = itemView.findViewById(R.id.myorder_number);
            time = itemView.findViewById(R.id.myorder_time);

//            itemNumber = itemView.findViewById(R.id.itemRow_itemNumber);
//            itemPrice = itemView.findViewById(R.id.itemRow_price);
//            itemQuantity = itemView.findViewById(R.id.itemRow_quantity);
//            itemDes = itemView.findViewById(R.id.itemRow_description);
//            itemDis = itemView.findViewById(R.id.itemRow_discount);
//            img = itemView.findViewById(R.id.itemRow_img);


        }
    }

    boolean contains(ArrayList<OrderDataModel> list, int name) {
        for (OrderDataModel item : list) {
            if (item.getId() == name) {
                return true;
            }
        }
        return false;
    }
    public void removeAt(int position) {

        OrderDataModelArrayList.remove(position);
        OrderDataModelAdapter.notifyItemRemoved(position);
        OrderDataModelAdapter.notifyItemRangeChanged(position, OrderDataModelArrayList.size());
    }
}