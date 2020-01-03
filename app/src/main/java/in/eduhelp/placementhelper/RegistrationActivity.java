package in.eduhelp.placementhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    TextView tv_heading;
    int [] errorids={R.id.tv_nameerror,R.id.tv_organizationerror,R.id.tv_designationerror,R.id.tv_experienceerror,R.id.tv_emailerror,R.id.tv_passworderror,R.id.tv_conpassworderror};
    int [] ids={R.id.et_name,R.id.et_orgname,R.id.et_designation,R.id.et_experience,R.id.et_email,R.id.et_password,R.id.et_conpassword};
    EditText [] ets=new EditText[ids.length];
    TextView [] errors=new TextView[errorids.length];
    String values[]=new String[ids.length];
    Button btn_reg;
    int i;
    String user_type;
    String url;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        Intent I=getIntent();
        user_type=I.getStringExtra("user_type");
        tv_heading=(TextView) findViewById(R.id.tv_heading);
        tv_heading.setText(user_type+" Registration");
        for(int i=0;i<ids.length;i++)
        {
            ets[i]=(EditText) findViewById(ids[i]);
            errors[i]=(TextView) findViewById(errorids[i]);
            errors[i].setVisibility(View.INVISIBLE);
        }

        if(user_type.equals("Employer"))
        {
            //url="http://phapp.estudyzone.org/employerreg.php";
            url="http://192.168.43.89/in.eduhelp.placementhelper/employerreg.php";
        }
        else
        {
            url="http://192.168.43.89/in.eduhelp.placementhelper/employeereg.php";
            ets[1].setText("nothing");
            findViewById(R.id.tv_layout_organization).setVisibility(View.GONE);
        }
        ets[ets.length-2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()<6)
                {
                    errors[errors.length-2].setText("Password too short.");
                    errors[errors.length-2].setTextColor(Color.RED);
                    errors[errors.length-1].setVisibility(View.VISIBLE);
                }
                else if(charSequence.toString().length()==6)
                {
                    errors[errors.length-2].setText("Medium Password.");
                    errors[errors.length-2].setTextColor(Color.YELLOW);
                    errors[errors.length-2].setVisibility(View.VISIBLE);
                }
                else
                {
                    errors[errors.length-2].setText("Strong password.");
                    errors[errors.length-2].setTextColor(Color.GREEN);
                    errors[errors.length-2].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ets[ets.length-1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(ets[ets.length-2].getText().toString().trim().equals(charSequence.toString()))
                {
                    errors[errors.length-1].setText("Password match.");
                    errors[errors.length-1].setTextColor(Color.GREEN);
                    errors[errors.length-1].setVisibility(View.VISIBLE);
                }
                else
                {
                    errors[errors.length-1].setText("Password not match.");
                    errors[errors.length-1].setTextColor(Color.RED);
                    errors[errors.length-1].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        requestQueue=Volley.newRequestQueue(getApplicationContext());
    }

    public void registerEmployerCode(View view) {
        for(i=0;i<ids.length;i++)
        {
            if(ets[i].getText().toString().trim().isEmpty())
            {
                errors[i].setText("Required this field.");
                errors[i].setTextColor(Color.RED);
                errors[i].setVisibility(View.VISIBLE);
                ets[i].requestFocus();
                break;
            }
            else
            {
                errors[i].setVisibility(View.INVISIBLE);
            }
        }
        if(i==ids.length)
        {
            for(i=0;i<ets.length;i++)
            {
                values[i]=ets[i].getText().toString().trim();
            }
            if(ets[ets.length-2].getText().toString().trim().length()>=6)
            {
                if(values[ets.length-1].equals(values[ets.length-2]))
                {
                    //Toast.makeText(RegistrationActivity.this, ""+values[0]+values[1]+values[2]+values[3]+values[4]+values[5], Toast.LENGTH_SHORT).show();
                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                            Intent I=new Intent(RegistrationActivity.this,DashBoardActivity.class);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrationActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> hm=new HashMap<String, String>();
                            hm.put("name",values[0]);
                            hm.put("orgname",values[1]);
                            hm.put("designation",values[2]);
                            hm.put("experience",values[3]);
                            hm.put("email",values[4]);
                            hm.put("password",values[5]);
                            if(!user_type.equals("Employer"))
                            {
                                hm.remove("orgname");
                            }
                            return hm;
                        }
                    };
                    requestQueue.add(request);
                }
                else
                {
                    errors[errors.length-1].setText("Password not match.");
                    ets[errors.length-1].requestFocus();
                    errors[errors.length-1].setTextColor(Color.RED);
                    errors[errors.length-1].setVisibility(View.VISIBLE);
                }
            }
            else
            {
                errors[errors.length-2].setText("Password is too short.");
                ets[errors.length-2].requestFocus();
                errors[errors.length-2].setTextColor(Color.RED);
                errors[errors.length-2].setVisibility(View.VISIBLE);
                errors[errors.length-1].setVisibility(View.INVISIBLE);
            }
        }
    }
}