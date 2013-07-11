package bottlerocket.smsDrivingApp;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.util.Log;

public class SMSMain extends Activity {

    private static boolean isDriving;
    private static Context mContext;
    public String customMessage;
    public CharSequence toastMessage = "State Set to \'Driving\'.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDriving = false;
        mContext = this;

        try{
            final EditText messageField = (EditText) findViewById(R.id.editText);
            customMessage = messageField.getText().toString();
        } catch(NullPointerException npe){
            Log.e("IANVAMOSSY:", "ERROR: STRING VALUE IS NULL. INPUT PROPER VALUE OR TELL DEV THAT HE CAN'T CODE.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smsmain, menu);
        return true;
    }

    public void onDrivingButtonClicked(View view) { //Defined in activity_main.xml under togglebutton
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            isDriving = true; //This updates properly
            Log.d("IANVAMOSSY:", "You are currently set to driving mode.");
            Toast toast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT);
            toast.show();

            if(customMessage != null){
                new SMSListener(mContext.getResources().getString(R.string.automatedWarning) + customMessage); //What happens if button is toggled repeatedly? Are old SMSListeners flushed?
            }

            else{
                new SMSListener(mContext.getResources().getString(R.string.automatedWarning) + mContext.getResources().getString(R.string.defaultMessage));
            }
        }

        else{
            isDriving = false;
        }
    }

    public static boolean isDriving(){
        return isDriving;
    }
}
