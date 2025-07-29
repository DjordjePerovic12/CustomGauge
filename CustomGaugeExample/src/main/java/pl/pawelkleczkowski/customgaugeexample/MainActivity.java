package pl.pawelkleczkowski.customgaugeexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import pl.pawelkleczkowskicustomgauge.R;

public class MainActivity extends AppCompatActivity {

	private CustomGauge gauge1;
	private CustomGauge gauge2;
	private CustomGauge gauge3;

	private TextView text1;
	private TextView text2;

	private Handler handler = new Handler(Looper.getMainLooper());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle(getString(R.string.app_name));
		setSupportActionBar(toolbar);

		Button button = findViewById(R.id.button);
		gauge1 = findViewById(R.id.gauge1);
		gauge2 = findViewById(R.id.gauge2);
		gauge3 = findViewById(R.id.gauge3);

		text1 = findViewById(R.id.textView1);
		text2 = findViewById(R.id.textView2);

		text1.setText(String.valueOf(gauge1.getValue()));
		text2.setText(String.format(Locale.getDefault(), "%1d/%2d", gauge2.getValue(), gauge2.getEndValue()));

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gauge2.setEndValue(800);
				gauge2.setValue(200);
				text2.setText(String.format(Locale.getDefault(), "%1d/%2d", gauge2.getValue(), gauge2.getEndValue()));

				new Thread(() -> {
					for (int i = 0; i < 100; i++) {
						int value1 = i * 10;
						int value2 = 200 + i * 5;
						int value3 = i;

						if (i == 50) {
							handler.post(() -> gauge2.setEndValue(1200));
						}

						handler.post(() -> {
							gauge1.setValue(value1);
							gauge2.setValue(value2);
							gauge3.setValue(value3);

							text1.setText(String.valueOf(value1));
							text2.setText(String.format(Locale.getDefault(), "%1d/%2d", value2, gauge2.getEndValue()));
						});

						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}
}
