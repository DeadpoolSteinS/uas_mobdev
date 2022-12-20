package edu.upi.cs.yudiwbs.uas_template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;


public class FragmentSatu extends Fragment {

    private FragmentSatuBinding binding;
    private ViewModelFragmentSatu model;

    public FragmentSatu() {
        // Required empty public constructor
    }

    public static FragmentSatu newInstance(String param1, String param2) {
        FragmentSatu fragment = new FragmentSatu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSatuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        model = new ViewModelProvider(getActivity()).get(ViewModelFragmentSatu.class);

        // observer
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvKeterangan.setText(s);
            }
        };

        // connect viewmodel and observer
        model.data.observe(getViewLifecycleOwner(), observer);

        final Observer<String> observer2 = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.keteranganJawaban.setText(s);
            }
        };

        // connect viewmodel and observer
        model.statusJawaban.observe(getViewLifecycleOwner(), observer2);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvKeterangan.setText("Loading...");

                ApiHargaBitcoin.get("random_joke", null, new JsonHttpResponseHandler() {
                    @Override
                    //hati2 success jsonobjek atau jsonarray
                    public void onSuccess(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          org.json.JSONObject response) {
                        Log.d("debugyudi", "onSuccess jsonobjek");
                        String joke = "";
                        try {
                            System.out.println(response);
                            joke = (String) response.get("setup");
                            String jawaban = (String) response.get("punchline");
                            model.setJawaban(jawaban);
                            Log.d("debugyudi", jawaban);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("debugyudi", "msg error" + ":" + e.getMessage());
                        }
                        binding.tvKeterangan.setText(joke);
                        model.setData(joke);
                    }

                    public void onSuccess(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          org.json.JSONArray response) {


                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String err, Throwable throwable) {
                        Log.e("debugyudi", "error " + ":" + statusCode + ":" + err);
                    }
                });

            }
        });

        binding.submitJawaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jawabanUser = binding.jawabanuser.getText().toString();
                String status = "";

                String jawabanBenarnyaUy = model.jawaban.getValue().toString();

                Log.d("debugyudi", "'" + jawabanBenarnyaUy + "'");
                Log.d("debugyudi", "'" + jawabanUser + "'");

                if(jawabanUser.equals(jawabanBenarnyaUy)){
                    status = "BENAR";
                    binding.keteranganJawaban.setText(status);
                }
                else{
                    status = "Jawaban salah, seharusnya: " + model.jawaban.getValue();
                    binding.keteranganJawaban.setText(status);
                }
                model.statusJawaban.setValue(status);
            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



