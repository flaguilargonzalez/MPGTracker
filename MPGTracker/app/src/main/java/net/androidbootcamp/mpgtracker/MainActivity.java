package net.androidbootcamp.mpgtracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    float decPriceGallon = 0;
    float decGallonspurchased = 0;
    float decMpg = 0;
    int intMilesTraveled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        final EditText milesTraveled = (EditText)findViewById(R.id.txtMilesTraveled);
        final EditText quantity =(EditText)findViewById(R.id.txtQuantity);
        final EditText pricePerGallon = (EditText)findViewById(R.id.txtPriceGal);
        final EditText date=(EditText)findViewById(R.id.txtDate);
        final TextView mpg = (TextView)findViewById(R.id.txtAvgMpgResult);

        Button btnCalculateMPG = (Button)findViewById(R.id.btnCalcMPG);
        Button btnClear = (Button)findViewById((R.id.btnClear));
        Button btnInstructions =(Button)findViewById(R.id.btnViewInstructions);
        Button btnMpgHistory   = (Button)findViewById(R.id.btnViewMPGHistory);

        //set the current date as default value for date TextView
        final String currentDateString = DateFormat.getDateInstance().format(new Date());
        date.setText(currentDateString);


        //Instantiate the DB
        final SQLDBAction dbActions = new SQLDBAction(this);
        final SQLiteDatabase db = dbActions.getWritableDatabase();
        final SQLiteDatabase dbReader = dbActions.getReadableDatabase();

        //calculate mpg click event listener
        btnCalculateMPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try
            {
                intMilesTraveled = Integer.parseInt(milesTraveled.getText().toString());
                decGallonspurchased = Float.parseFloat(quantity.getText().toString());
                decPriceGallon = Float.parseFloat(pricePerGallon.getText().toString());
                if (decGallonspurchased > 0 && intMilesTraveled > 0)
                {
                    decMpg = intMilesTraveled/decGallonspurchased;
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(0);
                    mpg.setText(df.format(decMpg));
                }

                //Insert Row into DB
                try
                {
                    //Insert values into database
                    String raw = "Insert Into MPGHist (Date, Price, NumGallons, Miles, MPG)" +
                            " Values (' " + dateFormat.format(new Date()) + "','   " + decPriceGallon +
                            "','  " + decGallonspurchased + "','  " + intMilesTraveled +
                            "','  " + decMpg + "')";
                    db.execSQL(raw);

                    //Clear Database Records
                    //String delete = "delete from MPGHist";
                    //db.execSQL(delete);

                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.toString() ,Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Invalid Entry",Toast.LENGTH_LONG).show();
            }
            }

        });

        //Clear button click event listener
        btnClear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                milesTraveled.setText("");
                pricePerGallon.setText("");
                quantity.setText("");
                mpg.setText("00");
            }
        });

        // ViewInstructions  button click event listener
        btnInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, InstructionsActivity.class));
            }
        });

        //view mpg history button click event listener
        btnMpgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MPGHistoryActivity.class));
            }
        });

    }
}
