package ucsc.cmps128.final_as2;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    InternetConnection ic;
    DownloadImage di;

    static TextView tv;
    static ImageView iv;

    ConnectivityManager cm;
    NetworkInfo ni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        iv = findViewById(R.id.image);

        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
    }


}
