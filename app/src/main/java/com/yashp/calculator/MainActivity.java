package com.yashp.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvCalculation, tvResult;
    private StringBuilder calculationExpression;
    private String lastOperator = "";
    private boolean isOperatorClicked = false;
    private double result = 0.0;
    private StringBuilder calculationHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvCalculation = findViewById(R.id.tv_calculation);
        tvResult = findViewById(R.id.tv_result);

        // Initialize variables
        calculationExpression = new StringBuilder();
        calculationHistory = new StringBuilder();

        setupNumberButtons();
        setupOperatorButtons();
        setupUtilityButtons();
    }

    private void setupNumberButtons() {
        int[] numberButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9
        };

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            String value = button.getText().toString();

            if (isOperatorClicked) {
                calculationExpression.append(" ");
                isOperatorClicked = false;
            }

            calculationExpression.append(value);
            tvCalculation.setText(calculationExpression.toString());
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtonIds = {
                R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply,
                R.id.btn_divide, R.id.btn_modulo
        };

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            String operator = button.getText().toString();

            if (!isOperatorClicked && calculationExpression.length() > 0) {
                calculationExpression.append(" ").append(operator);
                lastOperator = operator;
                isOperatorClicked = true;
            }
            tvCalculation.setText(calculationExpression.toString());
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupUtilityButtons() {
        findViewById(R.id.btn_equals).setOnClickListener(view -> {
            try {
                String[] tokens = calculationExpression.toString().split(" ");
                result = evaluateExpression(tokens);

                calculationExpression.append(" = ").append(result);
                tvCalculation.setText(calculationExpression.toString());
                tvResult.setText(String.valueOf(result));

                // Save history
                calculationHistory.append(calculationExpression.toString()).append("\n");

                // Reset for new calculation
                calculationExpression.setLength(0);
                isOperatorClicked = false;
            } catch (Exception e) {
                tvResult.setText("Error");
            }
        });

//        findViewById(R.id.btn_clear).setOnClickListener(view -> {
//            if (calculationExpression.length() > 0) {
//                calculationExpression.setLength(calculationExpression.length() - 1);
//                tvCalculation.setText(calculationExpression.toString());
//            }
//        });

        findViewById(R.id.btn_ac).setOnClickListener(view -> {
            calculationExpression.setLength(0);
            tvCalculation.setText("");
            tvResult.setText("0");
            isOperatorClicked = false;
            result = 0.0;
        });

        findViewById(R.id.btn_back).setOnClickListener(view -> {
            if (calculationExpression.length() > 0) {
                calculationExpression.deleteCharAt(calculationExpression.length() - 1);
                tvCalculation.setText(calculationExpression.toString());
            }
        });

        // Add a listener to show the calculation history (if needed)
//        findViewById(R.id.btn_history).setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
//            intent.putExtra("HISTORY", calculationHistory.toString());
//            startActivity(intent);
//        });

        findViewById(R.id.btn_history).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);

            // Pass the history data as a string
            intent.putExtra("HISTORY", calculationHistory.toString());

            // Start the HistoryActivity
            startActivity(intent);
        });
    }

    private double evaluateExpression(String[] tokens) {
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand != 0) {
                        result /= operand;
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
                case "%":
                    result %= operand;
                    break;
            }
        }

        return result;
    }
}
