package com.vijayjaidewan01vivekrai.collapsingtoolbar_github;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vijayjaidewan01vivekrai.collapsingtoolbar_github.Models.TestResults;
import com.vijayjaidewan01vivekrai.collapsingtoolbar_github.Models.User;
import com.vijayjaidewan01vivekrai.collapsingtoolbar_github.Okhttpclient.ApiService;
import com.vijayjaidewan01vivekrai.collapsingtoolbar_github.Okhttpclient.ApiUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    CardView card;
    TextInputEditText username, password;
    Button button;
    RelativeLayout relativeLayout;
    AppCompatImageView imageView,backImage;
    String url;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.login_fragment,container,false);

        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        card = view.findViewById(R.id.login_card);
        relativeLayout = view.findViewById(R.id.card_coordinator);
        imageView = view.findViewById(R.id.appLogo);
        button = view.findViewById(R.id.login_button);
        backImage = view.findViewById(R.id.back_image);

        username.setHint(getArguments().getString("Input_box1"));
        password.setHint(getArguments().getString("Input_box2"));
//            fragment.card.setCardBackgroundColor(Color.parseColor(login.getCard_bg_color()));
        Glide.with(this)
                .load(getArguments().getString("Profile_image"))
                .into(imageView);
        Glide.with(this)
                .load(getArguments().getString("Background_image"))
                .into(backImage);
//            fragment.password.setHighlightColor(Color.parseColor(login.getEdit_text_bg()));
//            fragment.username.setHighlightColor(Color.parseColor(login.getEdit_text_bg()));
//            fragment.relativeLayout.setBackgroundColor(Color.parseColor(login.getActivity_bg_color()));
//            fragment.button.setBackgroundColor(Color.parseColor(login.getButton_bg_color()));
//            fragment.button.setTextColor(Color.parseColor(login.getButton_text_color()));
        button.setText(getArguments().getString("Button_text"));
//        card.setAlpha(Float.parseFloat(getArguments().getString("Alpha")));

        url = getArguments().getString("Login_url");

        ScrollingActivity instance = new ScrollingActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getText().toString();
                Log.i("name", name);
                String pass = password.getText().toString();
                Log.i("pass", pass);


                //check the parameters to login
                if(!name.isEmpty() && !pass.isEmpty())
                {
                    relativeLayout.animate().translationYBy(-2000f).setDuration(300).alphaBy(1f);
                    UserLoginTask task = new UserLoginTask("abc","pass",url);
                    task.execute();
                    //startActivity(intent);
                }

            }
        });
        return view;
    }

    class UserLoginTask extends AsyncTask<Void, Void, String >
    {
        private final String username;
        private final String password;
        private final String url;

        public UserLoginTask(String username, String password, String url) {
            this.username = username;
            this.password = password;
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {

            ApiService apiService = ApiUtils.getAPIService();

            //apiService.results(url).enqueue();
            User user = new User();
            user.setInput_box1(username);
            user.setInput_box2(password);
            Call<TestResults> call = apiService.getUser(url, user);

            call.enqueue(new Callback<TestResults>() {
                @Override
                public void onResponse(Call<TestResults> call, Response<TestResults> response) {
                    Log.i("IN response","yes");
                    Toast.makeText(getContext(),response.body().getCode(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<TestResults> call, Throwable t) {

                }
            });

            return null;
        }
    }
}
