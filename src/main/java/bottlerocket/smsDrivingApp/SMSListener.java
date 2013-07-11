package bottlerocket.smsDrivingApp;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SMSListener extends BroadcastReceiver{

    private SmsManager smsManager = SmsManager.getDefault(); //Passes default instance of itself to smsManager object
    private static String number;
    private static String textMessage;
    private CharSequence toastMessage = "Text sent while driving!";

    public SMSListener(String message){
        textMessage = message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") && intent.getAction() != null){ //Crashes on text receive
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;

            if (bundle != null){
                Log.d("IANVAMOSSY:", "You got a text!");

                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        number = msgs[i].getOriginatingAddress();
                    }
                }
                catch(Exception e){
//                   Log.d("Exception caught",e.getMessage());
                }

                if(SMSMain.isDriving()){
                    smsManager.sendTextMessage(number, null, textMessage, null, null);
                    Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("IANVAMOSSY:", "Text sent while driving!");
                }
            }
        }
    }
}