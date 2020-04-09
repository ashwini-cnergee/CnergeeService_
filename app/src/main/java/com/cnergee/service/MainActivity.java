package com.cnergee.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.Window;

import com.cnergee.broadbandservice.R;

public class MainActivity extends Activity {

	private static final String PERMISSIONS_REQUIRED[] = new String[]{
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.ACCESS_WIFI_STATE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_NETWORK_STATE,
			Manifest.permission.CAMERA
	};

	private static final int REQUEST_PERMISSIONS = 110 ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		checkPermissions();

	}
	private void checkPermissions() {
		boolean permissionsGranted = checkPermission(PERMISSIONS_REQUIRED);
		if (permissionsGranted) {
			//if(!isUpdateFound){
			Thread splashThread = new Thread() {
				@Override
				public void run() {
					try {
						int waited = 0;
						while (waited < 2000) {
							sleep(100);
							waited += 100;
						}
					} catch (InterruptedException e) {
						// do nothing
					} finally {
						finish();

						Intent intent = new Intent(MainActivity.this, LoginActivity.class);
						intent.putExtra("from", "1");
						startActivity(intent);
					}
				}
			};
			splashThread.start();
			// }
		} else {
			boolean showRationale = true;
			for (String permission: PERMISSIONS_REQUIRED) {
				showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
				if (!showRationale) {
					break;
				}
			}

			String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

			new AlertDialog.Builder(this)
					.setTitle("Permissions Required")
					.setMessage(dialogMsg)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//dialog.dismiss();
							ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
						}
					}).create().show();
		}
	}

	private boolean checkPermission(String permissions[]) {
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == REQUEST_PERMISSIONS) {

			boolean hasGrantedPermissions = true;
			for (int i=0; i<grantResults.length; i++) {
				if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
					hasGrantedPermissions = false;
					break;
				}
			}

			if (!hasGrantedPermissions) {
				finish();
			}else {
				//if(!isUpdateFound){
				Thread splashThread = new Thread() {
					@Override
					public void run() {
						try {
							int waited = 0;
							while (waited < 2000) {
								sleep(100);
								waited += 100;
							}
						} catch (InterruptedException e) {
							// do nothing
						} finally {
							finish();

							Intent intent = new Intent(MainActivity.this, LoginActivity.class);
							intent.putExtra("from", "1");
							startActivity(intent);
						}
					}
				};
				splashThread.start();
			}
			// }
		} else {
			finish();
		}
	}


}
