package com.example.vehicleloancalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etVehiclePrice, etDownPayment, etLoanPeriod, etInterestRate;
    private TextView tvLoanAmount, tvTotalInterest, tvTotalPayment, tvMonthlyPayment;
    private Button btnCalculate, btnClear;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind views
        etVehiclePrice = findViewById(R.id.etVehiclePrice);
        etDownPayment = findViewById(R.id.etDownPayment);
        etLoanPeriod = findViewById(R.id.etLoanPeriod);
        etInterestRate = findViewById(R.id.etInterestRate);

        tvLoanAmount = findViewById(R.id.tvLoanAmount);
        tvTotalInterest = findViewById(R.id.tvTotalInterest);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvMonthlyPayment = findViewById(R.id.tvMonthlyPayment);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateLoan();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_about) {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    // stay on home
                    return true;
                }
                return false;
            }
        });
    }

    private void calculateLoan() {
        String sPrice = etVehiclePrice.getText().toString().trim();
        String sDown = etDownPayment.getText().toString().trim();
        String sYears = etLoanPeriod.getText().toString().trim();
        String sRate = etInterestRate.getText().toString().trim();

        if (TextUtils.isEmpty(sPrice) || TextUtils.isEmpty(sDown) ||
                TextUtils.isEmpty(sYears) || TextUtils.isEmpty(sRate)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(sPrice);
            double down = Double.parseDouble(sDown);
            double years = Double.parseDouble(sYears);
            double rate = Double.parseDouble(sRate);

            if (price < 0 || down < 0 || years <= 0 || rate < 0) {
                Toast.makeText(this, "Please enter valid positive values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (down > price) {
                Toast.makeText(this, "Down payment cannot exceed vehicle price", Toast.LENGTH_SHORT).show();
                return;
            }

            double loanAmount = price - down; // i
            double totalInterest = loanAmount * (rate / 100.0) * years; // ii
            double totalPayment = loanAmount + totalInterest; // iii
            double monthlyPayment = totalPayment / (years * 12.0); // iv

            tvLoanAmount.setText("Loan Amount: RM " + df.format(loanAmount));
            tvTotalInterest.setText("Total Interest: RM " + df.format(totalInterest));
            tvTotalPayment.setText("Total Payment: RM " + df.format(totalPayment));
            tvMonthlyPayment.setText("Monthly Payment: RM " + df.format(monthlyPayment));
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etVehiclePrice.setText("");
        etDownPayment.setText("");
        etLoanPeriod.setText("");
        etInterestRate.setText("");

        tvLoanAmount.setText("Loan Amount: RM -");
        tvTotalInterest.setText("Total Interest: RM -");
        tvTotalPayment.setText("Total Payment: RM -");
        tvMonthlyPayment.setText("Monthly Payment: RM -");
    }
}
