package com.pointtopoint.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pointtopoint.R;
import com.pointtopoint.api.RetrofitCon;
import com.pointtopoint.databinding.FragmentHomeBinding;
import com.pointtopoint.db.DatabaseHandler;
import com.pointtopoint.model.DataResponse;
import com.pointtopoint.model.DriverDataModel;
import com.pointtopoint.model.UserModel;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    DatabaseHandler db;
    UserModel userModel ;
    ProgressDialog progress;
    @BindView(R.id.profile_arname)
    TextView arname;
    @BindView(R.id.profile_name)
    TextView name;
    @BindView(R.id.profile_email)
    TextView email;
    @BindView(R.id.profile_phone)
    TextView phone;
    @BindView(R.id.profile_active_value)
    TextView active;
    @BindView(R.id.profile_sub_value)
    TextView sub;
    @BindView(R.id.profile_subtype_value)
    TextView subtype;
    @BindView(R.id.profile_orderstatus_value)
    TextView orderstatus;
    @BindView(R.id.profile_balance_value)
    TextView balance;
    @BindView(R.id.profile_subdate_value)
    TextView subdate;
    @BindView(R.id.profile_subdateex_value)
    TextView subdateex;
    @BindView(R.id.profile_ordercanceledcount_value)
    TextView ordercanceledcount;
    @BindView(R.id.profile_orderdonecount_value)
    TextView orderdonecount;
    @BindView(R.id.profile_idnumber_value)
    TextView idnumber;
    @BindView(R.id.profile_birthday_value)
    TextView birthday;
    @BindView(R.id.profile_gander_value)
    TextView gander;
    @BindView(R.id.profile_license_value)
    TextView license;
    @BindView(R.id.profile_idcard_value)
    TextView idcard;
    @BindView(R.id.profile_carid_value)
    TextView carid;
    @BindView(R.id.profile_carfront_value)
    TextView carfront;
    @BindView(R.id.profile_carback_value)
    TextView carback;

    @BindView(R.id.profile_balance_row)
    TableRow balancerow;
    @BindView(R.id.profile_sub_row)
    TableRow subrow;
    @BindView(R.id.profile_subdate_row)
    TableRow daterow;
    @BindView(R.id.profile_subdateex_row)
    TableRow dateexrow;

    String idcardS;
    String caridS;
    String carbackS;
    String carfrontS;
    String licenseS;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ButterKnife.bind(this,root);

        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(getContext());
        userModel = db.getUser();
//        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }

        });


        getData();

        idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openimage(idcardS);
            }
        });
        carid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage(caridS);

            }
        });
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage(licenseS);

            }
        });
        carback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage(carbackS);

            }
        });
        carfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage(carfrontS);

            }
        });
        return root;
    }


    void getData(){
        new RetrofitCon(getContext()).getService().getDriverData("Bearer "+userModel.getAccessToken(),db.getUser().getId()).enqueue(new Callback<DataResponse<DriverDataModel>>() {
            @Override
            public void onResponse(Call<DataResponse<DriverDataModel>> call, Response<DataResponse<DriverDataModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
//                        response.body().getUser().setAccessToken(response.body().getAccessToken());

                        arname.setText(response.body().getData().getA_name());
                        name.setText(response.body().getData().getName());
                        email.setText(response.body().getData().getEmail());
                        phone.setText(String.valueOf(response.body().getData().getPhone()));
                        switch (response.body().getData().getDriverdata().getActive()){
                            case 0:
                                active.setText("غير فعال");
                                active.setTextColor(Color.RED);
                                break;

                            case 1:
                                active.setText("فعال");
                                active.setTextColor(Color.GREEN);
                                break;
                        }
                        switch (response.body().getData().getDriverdata().getIs_sub_or_percen()){

                            case 0:
                                subrow.setVisibility(View.GONE);
                                dateexrow.setVisibility(View.GONE);
                                daterow.setVisibility(View.GONE);
                                balance.setText(String.valueOf(response.body().getData().getDriverdata().getBalance()));

                                subtype.setText("نسبة");
                                break;

                            case 1:
                                balancerow.setVisibility(View.GONE);
                                subdate.setText(response.body().getData().getDriverdata().getSubscribed_date());
                                subdateex.setText(response.body().getData().getDriverdata().getSubscribed_ex_date());
                                subtype.setText("اشتراك");

                                break;
                        }
                        switch (response.body().getData().getDriverdata().getSubscribed()){
                            case 0:
                                sub.setText("غير مشترك");
                                sub.setTextColor(Color.RED);
                                break;

                            case 1:
                                sub.setText("مشترك");
                                sub.setTextColor(Color.GREEN);
                                break;
                        }

                        switch (response.body().getData().getDriverdata().getStatus()){
                            case 0:
                                orderstatus.setText("لا يوجد لديك طلب");
                                orderstatus.setTextColor(Color.GREEN);
                                break;

                            case 1:
                                orderstatus.setText("يوجد لديك طلب");
                                orderstatus.setTextColor(Color.RED);
                                break;
                        }
                        ordercanceledcount.setText(String.valueOf(response.body().getData().getDriverdata().getOrders_count_canceled()));
                        orderdonecount.setText(String.valueOf(response.body().getData().getDriverdata().getOrders_count()));
                        idnumber.setText(String.valueOf(response.body().getData().getDriverdata().getId_number()));
                        birthday.setText(response.body().getData().getDriverdata().getBirth_date());
                        gander.setText(response.body().getData().getDriverdata().getGender());

                        carfrontS = response.body().getData().getDriverdata().getCar_p_f();
                        carbackS = response.body().getData().getDriverdata().getCar_p_r();
                        idcardS = response.body().getData().getDriverdata().getId_card();
                        caridS = response.body().getData().getDriverdata().getId_car();
                        licenseS = response.body().getData().getDriverdata().getLicense();

                        Toast.makeText(getContext(), response.body().getData().getA_name(), Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {

                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        Log.e("ApiError",jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DataResponse<DriverDataModel>> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
                progress.dismiss();
            }
        });
    }

    void openimage(String url){
//        if (!url.startsWith("http://") && !url.startsWith("https://"))
//            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}