package in.eduhelp.placementhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashBoardActivity extends AppCompatActivity {

    Button btn_login_employee,btn_login_employer;
    EditText et_employer_userid,et_employer_password,et_employee_userid,et_employee_password;
    RequestQueue requestQueue;
    String employerurl="http://192.168.43.89/in.eduhelp.placementhelper/employerlog.php";
    String employeeurl="http://192.168.43.89/in.eduhelp.placementhelper/employeelog.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().hide();
        btn_login_employee=(Button) findViewById(R.id.btn_login_employee);
        btn_login_employer=(Button) findViewById(R.id.btn_login_employer);

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        FragmentEmployee fe=new FragmentEmployee();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.rl_login,fe);
        btn_login_employee.setBackgroundResource(R.drawable.fg_btn_half);
        btn_login_employer.setBackgroundResource(R.drawable.fg_btn_full);
        ft.commit();
    }

    public void loginEmployee(View view) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        FragmentEmployee fe=new FragmentEmployee();
        ft.add(R.id.rl_login,fe);
        btn_login_employee.setBackgroundResource(R.drawable.fg_btn_half);
        btn_login_employer.setBackgroundResource(R.drawable.fg_btn_full);
        ft.commit();
    }
    public void loginEmployer(View view) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        FragmentEmployer fe=new FragmentEmployer();
        ft.add(R.id.rl_login,fe);
        btn_login_employer.setBackgroundResource(R.drawable.fg_btn_half);
        btn_login_employee.setBackgroundResource(R.drawable.fg_btn_full);
        ft.commit();
    }
    public void registerEmployee(View view){
        Intent I=new Intent(DashBoardActivity.this,RegistrationActivity.class);
        I.putExtra("user_type","Employee");
        startActivity(I);
    }
    public void registerEmployer(View view){
        Intent I=new Intent(DashBoardActivity.this,RegistrationActivity.class);
        I.putExtra("user_type","Employer");
        startActivity(I);
    }
    public void LoginEmployer(View view)
    {
        et_employer_userid=(EditText) findViewById(R.id.et_employer_userid);
        et_employer_password=(EditText) findViewById(R.id.et_employer_password);
        if(et_employer_userid.getText().toString().trim().isEmpty())
        {
            et_employer_userid.setError("Empty");
            et_employer_userid.requestFocus();
        }
        else if(et_employer_password.getText().toString().trim().isEmpty())
        {
            et_employer_password.setError("Empty");
            et_employer_password.requestFocus();
        }
        else
        {

            StringRequest request=new StringRequest(Request.Method.POST, employerurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.trim().equals("User not exist."))
                    {
                        et_employer_userid.setError("User not exist.");
                        et_employer_userid.requestFocus();
                    }
                    else if(response.trim().equals("Incurrect password."))
                    {
                        et_employer_password.setError("Incurrect password.");
                        et_employer_password.requestFocus();
                    }
                    else
                    {
                        try {
                            JSONObject res=new JSONObject(response);
                            JSONArray employer=res.getJSONArray("employer");
                            JSONObject data=employer.getJSONObject(0);
                            Toast.makeText(DashBoardActivity.this, ""+data.getString("name"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DashBoardActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hm=new HashMap<String, String>();
                    hm.put("userid",et_employer_userid.getText().toString().trim());
                    hm.put("password",et_employer_password.getText().toString().trim());
                    return hm;
                }
            };
            requestQueue.add(request);
        }
    }

    public void Loginemployee(View view) {
        et_employee_userid = (EditText) findViewById(R.id.et_employee_userid);
        et_employee_password = (EditText) findViewById(R.id.et_employee_password);
        if (et_employee_userid.getText().toString().trim().isEmpty()) {
            et_employee_userid.setError("Empty");
            et_employee_userid.requestFocus();
        } else if (et_employee_password.getText().toString().trim().isEmpty()) {
            et_employee_password.setError("Empty");
            et_employee_password.requestFocus();
        } else {

            StringRequest request = new StringRequest(Request.Method.POST, employeeurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("User not exist.")) {
                        et_employee_userid.setError("User not exist.");
                        et_employee_userid.requestFocus();
                    } else if (response.trim().equals("Incurrect password.")) {
                        et_employee_password.setError("Incurrect password.");
                        et_employee_password.requestFocus();
                    } else {
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray employee = res.getJSONArray("employee");
                            JSONObject data = employee.getJSONObject(0);
                            Toast.makeText(DashBoardActivity.this, "" + data.getString("name"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DashBoardActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hm = new HashMap<String, String>();
                    hm.put("userid", et_employee_userid.getText().toString().trim());
                    hm.put("password", et_employee_password.getText().toString().trim());
                    return hm;
                }
            };
            requestQueue.add(request);
        }
    }
}
