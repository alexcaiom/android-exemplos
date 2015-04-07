package com.alex.mapas;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText input = null;
	TextView output = null;
	Button btnPesquisar = null;
	CheckBox chkEhEndereco = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		input = (EditText) findViewById(R.id.input_txt);
		output = (TextView) findViewById(R.id.output);
		btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
		chkEhEndereco = (CheckBox) findViewById(R.id.input_check);
		
		btnPesquisar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				output.setText(performGeocode(input.getText().toString(), chkEhEndereco.isChecked()));
				
			}
		});
		
	}

	protected String performGeocode(String destino, boolean ehEndereco) {
		String resultado = "Não consegui localizar: " + destino;
		
		if (input != null) {
			Geocoder geocoder = new Geocoder(this);
			if (ehEndereco) {
				try{
					List<Address> enderecos = geocoder.getFromLocationName(destino, 1);
					if (enderecos != null && !enderecos.isEmpty()) {
						resultado = enderecos.get(0).toString();
					}
				}catch(IOException e){
					Toast.makeText(this, "Erro: "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			} else {
				String[] coordenadas = destino.split(",");
				if ((coordenadas != null) && (coordenadas.length == 2)) {
					try {
						List<Address> enderecos = geocoder.getFromLocation(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]), 1);
						if (enderecos != null && !enderecos.isEmpty()) {
							resultado = enderecos.get(0).toString();
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return resultado;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
