package com.smk.siakad;

import android.widget.Filter;

import com.smk.siakad.adapter.AdapterSiswa;
import com.smk.siakad.model.Siswa;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    private AdapterSiswa adapterSiswa;
    private ArrayList<Siswa> filterList;

    public CustomFilter(ArrayList<Siswa> filterList,AdapterSiswa adapterSiswa)
    {
        this.adapterSiswa=adapterSiswa;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Siswa> filteredSiswa=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredSiswa.add(filterList.get(i));
                }
            }

            results.count=filteredSiswa.size();
            results.values=filteredSiswa;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterSiswa.siswa= (ArrayList<Siswa>) results.values;

        //REFRESH
        adapterSiswa.notifyDataSetChanged();

    }
}
