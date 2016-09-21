package com.fusemobiletest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fusemobiletest.classes.CompanyResponse;
import com.fusemobiletest.network.ApiHelper;
import com.fusemobiletest.network.URLConstants;
import com.fusemobiletest.utility.UtilityMethods;
import com.fusemobiletest.utility.VolleySingleton;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainActivity extends AppCompatActivity {

    EditText companyName;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        companyName = (EditText) findViewById(R.id.company_name);
        logo = (ImageView) findViewById(R.id.logo);

        //Initialise the ImageLoader for loading the logo
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(config);

        companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName.setBackgroundColor(Color.WHITE);
                logo.setVisibility(View.GONE);
            }
        });

        //Fires when the user clicks the done button on the keyboard.
        companyName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String enteredText = textView.getText().toString();

                    //Clears all the whitespace from the entered text
                    enteredText = UtilityMethods.removeWhitespaceFromText(enteredText);

                    //Checks if the text has a length over 1
                    if (UtilityMethods.isStringValidLength(enteredText)) {
                        sendCompanyCheck(enteredText);
                    } else {
                        companyName.setBackgroundColor(Color.RED);
                    }

                    handled = true;
                }
                return handled;
            }
        });
    }

    private void sendCompanyCheck(String enteredText) {

        StringRequest fuseCompanyRequest = ApiHelper.buildApiStringCall(
            Request.Method.GET,
            URLConstants.URLS.COMPLETE_URL(enteredText),
            null,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    companyName.setBackgroundColor(Color.GREEN);
                    Gson gson = new Gson();
                    CompanyResponse companyResponse = gson.fromJson(response, CompanyResponse.class);
                    if (companyResponse != null) {
                        companyName.setText(companyResponse.getName());
                        loadLogo(companyResponse.getLogo());
                    }
                    //updateCardList();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    companyName.setBackgroundColor(Color.RED);
                }
            });
        fuseCompanyRequest.setTag(URLConstants.CALL_FUSION_CHECK);
        VolleySingleton.getInstance(this).addToRequestQueue(fuseCompanyRequest);
    }

    //Uses the image loader to load the logo.
    public void loadLogo(String logoUrl){
        ImageLoader.getInstance().displayImage(
                logoUrl, logo);
        logo.setVisibility(View.VISIBLE);
    }
}
