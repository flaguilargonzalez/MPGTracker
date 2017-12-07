package net.androidbootcamp.mpgtracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;


public class MPGHistoryActivity extends AppCompatActivity {
    float decPriceGallon =0;
    float decGallonspurchased=0;
    float decMpg=0;
    String dateEntered;
    int intMilesTraveled;
    private TableLayout tableLayout;
    String currentDateString = DateFormat.getDateInstance().format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpghistory);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        Button btnReturn =(Button)findViewById(R.id.btnReturn);
        Button btnPrint = (Button)findViewById(R.id.btnPrint);

        //Instantiate the DB
        final SQLDBAction dbActions = new SQLDBAction(this);
        final SQLiteDatabase db = dbActions.getReadableDatabase();

        //Populate Table
        View tableRow = LayoutInflater.from(this).inflate(R.layout.activity_mpghistory_table,null,false);
        TextView Column1  = (TextView) tableRow.findViewById(R.id.Column1);
        TextView Column2  = (TextView) tableRow.findViewById(R.id.Column2);
        TextView Column3  = (TextView) tableRow.findViewById(R.id.Column3);
        TextView Column4  = (TextView) tableRow.findViewById(R.id.Column4);
        TextView Column5  = (TextView) tableRow.findViewById(R.id.Column5);


        //Header
        Column1.setText("Date");
        Column2.setText("Price");
        Column3.setText("#Gallons");
        Column4.setText("Miles");
        Column5.setText("MPG");
        tableLayout.addView(tableRow);

        //Cursor Loop to get rows from the db
        String query =  "Select * from MPGHist";
        Cursor c = db.rawQuery(query, null);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        int i = 1;

        if(c!=null && c.getCount()>0)
        {
            try
            {
                if(c.moveToFirst())
                {
                    do {
                        TableRow row = new TableRow(this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(lp);
                        TextView cDate = new TextView(this);
                        TextView cPrice = new TextView(this);
                        TextView cNumGallons = new TextView(this);
                        TextView cMiles = new TextView(this);
                        TextView cMPG = new TextView(this);
                        cDate.setText(c.getString(c.getColumnIndex("Date")));
                        cPrice.setText(c.getString(c.getColumnIndex("Price")));
                        cNumGallons.setText(c.getString(c.getColumnIndex("NumGallons")));
                        cMiles.setText(c.getString(c.getColumnIndex("Miles")));
                        cMPG.setText(c.getString(c.getColumnIndex("MPG")));
                        row.addView(cDate);
                        row.addView(cPrice);
                        row.addView(cNumGallons);
                        row.addView(cMiles);
                        row.addView(cMPG);
                        tl.addView(row,i);
                        i++;
                    } while (c.moveToNext());
                }
            }
            catch (Exception e)
            {
                Toast.makeText(MPGHistoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        //btnReturn click event listener
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MPGHistoryActivity.this, MainActivity.class));
            }
        });

        //btnPrint click event listener
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
