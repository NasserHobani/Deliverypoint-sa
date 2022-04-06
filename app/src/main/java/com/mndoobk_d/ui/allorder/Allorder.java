package com.mndoobk_d.ui.allorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mndoobk_d.R;
import com.mndoobk_d.adapter.Adapter;
import com.mndoobk_d.api.RetrofitCon;
import com.mndoobk_d.databinding.FragmentDashboardBinding;
import com.mndoobk_d.db.DatabaseHandler;
import com.mndoobk_d.model.DataResponse;
import com.mndoobk_d.model.OrderDataModel;
import com.mndoobk_d.model.UserModel;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Allorder extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    Adapter<OrderDataModel> OrderDataModelAdapter;
    ArrayList<OrderDataModel> OrderDataModelArrayList;
    RecyclerView recyclerView;

    @BindView(R.id.myorder_search)
    SearchView searchView;
//    @BindView(R.id.myorder_rcvl)
//    RecyclerView rcvl;
    int id;
    String name;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;
    public static Allorder newInstance() {

        return new Allorder();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ButterKnife.bind(this,root);
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        recyclerView = root.findViewById(R.id.myorder_rcvl);
        db = new DatabaseHandler(getContext());
        user= db.getUser();
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        OrderDataModelArrayList = new ArrayList<>();

        rcvl(OrderDataModelArrayList);
        showData();
        getData();
        search();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    void showData(){
        OrderDataModelArrayList.clear();

        OrderDataModelArrayList.addAll(db.getOrders());

        OrderDataModelAdapter.notifyDataSetChanged();


    }
    private void search() {
        ArrayList<OrderDataModel> filteredList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredList.clear();
                for (OrderDataModel item : OrderDataModelArrayList) {
                    String number = String.valueOf(item.getId());
                    if (number.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                OrderDataModelAdapter.filterList(filteredList);




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    rcvl(OrderDataModelArrayList);
                } else {
                    filteredList.clear();

                    for (OrderDataModel item : OrderDataModelArrayList) {
                        String number = String.valueOf(item.getId());
                        if (number.toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    OrderDataModelAdapter.filterList(filteredList);

                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                rcvl(OrderDataModelArrayList);
                return false;
            }
        });

    }
    void getData(){
//        progress.show();
        new RetrofitCon(getContext()).getService().getOrder("Bearer "+user.getAccessToken()).enqueue(new Callback<DataResponse<OrderDataModel>>() {
            @Override
            public void onResponse(Call<DataResponse<OrderDataModel>> call, Response<DataResponse<OrderDataModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");
                        if (response.body().getDatas().size() != db.getOrdersCount()) {
                            progress.show();
                            db.deleteOrders();
                            for (OrderDataModel orderDataModel : response.body().getDatas()) {
                                db.addoOrder(orderDataModel);

                            }
                            showData();
                        }else {
                            for (OrderDataModel orderDataModel : response.body().getDatas()) {
                                db.addoOrder(orderDataModel);

                            }
                            showData();

                        }
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
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
                adapterHolder.price.setText(val.getPrice());
                adapterHolder.status.setText(val.getStatus());
                adapterHolder.number.setText("+966"+String.valueOf(val.getPhone()) + " Go to Whatsapp");
                adapterHolder.number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://api.whatsapp.com/send?phone=+966"+val.getPhone();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                String inputPattern = "yyyy-MM-dd HH:mm";
                String outputPattern = "dd-MMM-yyyy h:mm a";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


                try {
                    if (val.getTime() != null && !val.getTime().isEmpty()) {

                        String newstdate = val.getTime().replace("T"," ");
                        Date time = inputFormat.parse(newstdate);

                        assert time != null;
                        adapterHolder.time.setText(outputFormat.format(time));
                    }


                    if (val.getReceivetime() != null && !val.getReceivetime().isEmpty()) {
                        Date recivedate =  inputFormat.parse(val.getReceivetime());

                        assert recivedate != null;
                        adapterHolder.recivedtime.setText(outputFormat.format(recivedate));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                switch (val.getStatus_id()){

                    case 2:
                        try {



                            if (val.getReceivetime() != null && !val.getReceivetime().isEmpty()) {
                                Date recivedate = inputFormat.parse(val.getReceivetime());
                                assert recivedate != null;
//                    Date targetTime = DateUtils.addMinutes(targetTime, addMinuteTime);
                                adapterHolder.recivedtime.setText(outputFormat.format(recivedate));

                                Calendar targetTime = Calendar.getInstance();
                                targetTime.setTime(recivedate);
                                targetTime.add(Calendar.MINUTE, 40);
                                Date newdate = targetTime.getTime();
                                Calendar now = Calendar.getInstance();
                                int diff = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    diff = Math.toIntExact(printDifference(now.getTime(), newdate));
                                }
                                if(diff > 0){
                                    int finalDiff = diff;
                                    new CountDownTimer((finalDiff *60000), 60000) {

                                        public void onTick(long millisUntilFinished) {
                                            adapterHolder.btn.setText("يمكنك تسليم الطلب بعد " + (millisUntilFinished/60000));
                                            adapterHolder.btn.setEnabled(false);
                                        }

                                        public void onFinish() {
                                            adapterHolder.btn.setText("تسليم");
                                            adapterHolder.btn.setEnabled(true);
                                        }
                                    }.start();
                                }else{
                                    adapterHolder.btn.setText("تسليم");
                                    adapterHolder.btn.setEnabled(true);
                                }

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        adapterHolder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progress.show();

                                new RetrofitCon(getContext()).getService().closeorder("Bearer "+user.getAccessToken(),"3",val.getId()).enqueue(new Callback<DataResponse<OrderDataModel>>() {
                                    @Override
                                    public void onResponse(Call<DataResponse<OrderDataModel>> call, Response<DataResponse<OrderDataModel>> response) {
                                        if (response.isSuccessful()){
                                            if (response.body().isSuccess()){

                                                adapterHolder.btn.setEnabled(false);
                                                OrderDataModelArrayList.get(i).setStatus("تم تسليم الطلب");
                                                OrderDataModelAdapter.notifyDataSetChanged();

                                            }
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

                        break;

                    default:
                        adapterHolder.btn.setText(val.getStatus());
                        adapterHolder.btn.setEnabled(false);
                        break;
                }






            }
        };
        recyclerView.setAdapter(OrderDataModelAdapter);

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
        TextView id,from,to,status,price,distance,storename,number,recivedtime,time;

        Button btn;
        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.myorder_orderid);
            from = itemView.findViewById(R.id.myorder_from);
            to = itemView.findViewById(R.id.myorder_to);
            status = itemView.findViewById(R.id.myorder_status);
            price = itemView.findViewById(R.id.myorder_price);
            distance = itemView.findViewById(R.id.myorder_distance);
            storename = itemView.findViewById(R.id.myorder_storname);
            number = itemView.findViewById(R.id.myorder_number);
            btn = itemView.findViewById(R.id.myorder_btn);
            time = itemView.findViewById(R.id.myorder_time);
            recivedtime = itemView.findViewById(R.id.myorder_accptedtime);
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