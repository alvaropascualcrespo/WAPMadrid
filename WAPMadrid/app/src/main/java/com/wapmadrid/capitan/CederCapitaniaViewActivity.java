package com.wapmadrid.capitan;

import java.util.ArrayList;
import java.util.List;

import com.wapmadrid.R;
import com.wapmadrid.data.ItemCederCapitania;

import android.app.ListActivity;
import android.os.Bundle;

public class CederCapitaniaViewActivity extends ListActivity{

	List<ItemCederCapitania> rows ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitan_ceder_capitania);
        fill();
        
    }

	private void fill() {
		// TODO Auto-generated method stub
		
		/*rows = new ArrayList<ItemCederCapitania>(30);
        ItemCederCapitania row = null;
        for (int i = 1; i < 31; i++)
        {
            row = new Row();
            row.setNombre("Title " + i);
            row.setSubtitle("Subtitle " + i);
            rows.add(row);
        }
        setListAdapter(new CustomArrayAdapter(this, rows));
         
        getListView().setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this, rows.get(position).getTitle(), Toast.LENGTH_SHORT).show();                
            }
        });*/
		
	}
	
}
