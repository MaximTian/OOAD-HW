package mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maximtian.myapplication.R;
import com.example.maximtian.myapplication.login_activity;

/**
 * Created by WILLIAM on 2016/7/13.
 */
public class Register_Activity extends Activity {
    private Button registerBtn;
    private EditText acount;
    private EditText password;
    private EditText confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerBtn = (Button)findViewById(R.id.register_registerBtn);
        acount = (EditText)findViewById(R.id.register_account);
        password = (EditText)findViewById(R.id.register_password);
        confirm_password = (EditText)findViewById(R.id.register_confirm);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(confirm_password.getText().toString()) &&
                        !password.getText().toString().equals("")) {
                    Intent intent = new Intent(Register_Activity.this, login_activity.class);
                    startActivity(intent);
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(Register_Activity.this, "����������", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Register_Activity.this, "�����������벻һ��", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}