package com.example.amira.rechercheresterant;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;

/**
 * Created by Amira on 03/02/2018.
 */

public class Connection {

    private Context context;

    public Connection(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;

            }
        }
        return false;
    }

}
