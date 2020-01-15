package ru.mospolytech.contactsbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mospolytech.contactsbook.DBHelper;
import ru.mospolytech.contactsbook.R;

public class DisplayContact extends AppCompatActivity {
    private DBHelper mydb;
    TextView surname;
    TextView name;
    TextView secname;
    TextView phone;
    TextView email;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        surname = (TextView) findViewById(R.id.editTextSurName);
        name = (TextView) findViewById(R.id.editTextName);
        secname = (TextView) findViewById(R.id.editTextSecName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextEmail);


        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        {
            int Value = extras.getInt("id");

            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String sname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String suname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SURNAME));
                String sename = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SECNAME));
                String sphone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String semail = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));


                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) sname);
                name.setFocusable(false);
                name.setClickable(false);
                surname.setText((CharSequence) suname);
                surname.setFocusable(false);
                surname.setClickable(false);
                secname.setText((CharSequence) sename);
                secname.setFocusable(false);
                secname.setClickable(false);

                phone.setText((CharSequence) sphone);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence) semail);
                email.setFocusable(false);
                email.setClickable(false);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.menu_display_contact, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                surname.setEnabled(true);
                surname.setFocusableInTouchMode(true);
                surname.setClickable(true);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                secname.setEnabled(true);
                secname.setFocusableInTouchMode(true);
                secname.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);


                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure ?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void saveData(View view) {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateContact(id_To_Update, surname.getText().toString(),name.getText().toString(),secname.getText().toString(), phone.getText().toString(),email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Record not updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.addContact(surname.getText().toString(),name.getText().toString(),secname.getText().toString(),phone.getText().toString(), email.getText().toString()  )) {
                    Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Record not added", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
