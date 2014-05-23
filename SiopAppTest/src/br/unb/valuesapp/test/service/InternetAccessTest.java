package br.unb.valuesapp.test.service;

import junit.framework.Assert;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.AndroidTestCase;

public class InternetAccessTest extends AndroidTestCase{
	
	public void testInternetAccess(){
		
		Context myContext;
		ConnectivityManager manager;
		NetworkInfo info;
		
		myContext = getContext();
		manager = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		Assert.assertNotNull(info);
		Assert.assertTrue(info.isConnectedOrConnecting());
		
	}

}
