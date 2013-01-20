/*************************************
 * Package Declaration
 *************************************/
package com.abelanet.genpasswd;

/**************************************************************************************************************** 
 * Java & Android Imports
 ****************************************************************************************************************/
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**************************************************************************************************************** 
 * Main Activity - First Screen
 ****************************************************************************************************************/

public class MainActivity extends Activity implements OnSeekBarChangeListener {

	/**************************************************************************************************************** 
	 * Global variables Declaration
	 ****************************************************************************************************************/
	CheckBox chk1; // Lower Case Letters
	CheckBox chk2; // Upper Case Letters
	CheckBox chk3; // Numbers
	CheckBox chk4; // Symbols
	SeekBar sb; // SeekBar to Select length of Password
	TextView tv1; // TextView holding the Generated Password
	TextView tv2; // TextView that displays the length of the Password

	String text; // String that will hold the combination of 
	int l;

	/**************************************************************************************************************** 
	 * Main Activity On Create
	 ****************************************************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialiseApp();
	}

	
	/**************************************************************************************************************** 
	 * Initialisation of Global Variables and Main App
	 ****************************************************************************************************************/
	public void initialiseApp() {
		chk1 = (CheckBox) findViewById(R.id.checkBox1);
		chk2 = (CheckBox) findViewById(R.id.checkBox2);
		chk3 = (CheckBox) findViewById(R.id.checkBox3);
		chk4 = (CheckBox) findViewById(R.id.checkBox4);
		sb = (SeekBar) findViewById(R.id.seekBar1);
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);

		sb.setOnSeekBarChangeListener(this);

		chk1.setChecked(true); // Set to true to generate password from lower case letters
		chk2.setChecked(false);
		chk3.setChecked(false);
		chk4.setChecked(false);

		l = 10; // Default length of Password
		sb.setProgress(10);
		tv2.setText("Length : 10");

		text = "abcdefghijklmnopqrstuvwxyz"; // Initialise it with lower case letters
	}

	/**************************************************************************************************************** 
	 * Checks each Checkbox and depending on those selected it formats a string with the characters to generate from
	 ****************************************************************************************************************/
	public void onCheckBox(View view) {
		Button bt = (Button) findViewById(R.id.button1);
		text = "";
		if (chk1.isChecked()) {
			text = text + "abcdefghijklmnopqrstuvwxyz";
		}
		if (chk2.isChecked()) {
			text = text + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		if (chk3.isChecked()) {
			text = text + "0123456789";
		}
		if (chk4.isChecked()) {
			text = text + "!@#$%^&*()_+-=~`{}|[]\\\":;'<>?,./'";
		}
		if (text == ""){
			Toast.makeText(this, "Please select something", Toast.LENGTH_LONG).show();
			bt.setEnabled(false);
		}
		else {
			bt.setEnabled(true);
			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		}
	}

	/**************************************************************************************************************** 
	 * Function to generate the Password from the character string
	 ****************************************************************************************************************/
	public void onClickButton(View view) {
		Random rnd = new Random();
		String s = "";

		for (int i = 0; i < 10; i++) {
			s = genStr(rnd, text, l);
		}
		tv1.setText("");
		tv1.setText(s);
	}

	/**************************************************************************************************************** 
	 * This method generated a random string from a specified string with a random length
	 * 
	 * @param r is the random generator
	 * @param s is a string of characters from which the string is to be generated
	 * @param length of string to be generated
	 * @return Generated String
	 * 
	 ****************************************************************************************************************/
	public String genStr(Random r, String s, int length) {
		char[] rndtext = new char[length];
		for (int i = 0; i < length; i++) {
			rndtext[i] = s.charAt(r.nextInt(s.length()));
		}

		// Just note how the char array text is passed a string return value
		String rndStr = new String(rndtext);

		return rndStr;
	}

	/**************************************************************************************************************** 
	 * Display the Application Menu
	 ****************************************************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	/**************************************************************************************************************** 
	 * SeekBar method - When the slider is moving
	 ****************************************************************************************************************/
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		updateLength();
	}

	/**************************************************************************************************************** 
	 * SeekBar method - When the slider starts moving
	 ****************************************************************************************************************/
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		updateLength();
	}

	/**************************************************************************************************************** 
	 * SeekBar method - When the slider ends moving
	 ****************************************************************************************************************/
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		updateLength();
	}

	/**************************************************************************************************************** 
	 * SeekBar method - When the slider is moving update the text under the seekbar & get last current value
	 ****************************************************************************************************************/
	public void updateLength() {
		l = sb.getProgress(); // Get the current value of the seekbar
		tv2.setText("Length : " + l);
	}

	/**************************************************************************************************************** 
	 * SeekBar method - When the slider is moving update the text under the seekbar & get last current value
	 ****************************************************************************************************************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean retval;

		switch (item.getItemId()) {
		case R.id.menu_about:
	    	Intent aboutIntent = new Intent(this, AboutActivity.class);
	    	startActivity(aboutIntent);
			Toast.makeText(this, "About Selected", Toast.LENGTH_LONG).show();
			retval = true;
			break;
		default:
			/*
			 * call onOptionsItemSelected on super - this is better than just
			 * returning false, even though it is most likely that super will
			 * not respond to a menu item selection. (It might...)
			 */
			retval = super.onOptionsItemSelected(item);
		}
		return retval;
	}
}
