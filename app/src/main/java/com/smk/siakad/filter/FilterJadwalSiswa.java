package com.smk.siakad.filter;

import android.widget.Filter;

import com.smk.siakad.adapter.AdapterJadwalSiswa;
import com.smk.siakad.model.JadwalSiswa;

import java.util.ArrayList;

public class FilterJadwalSiswa extends Filter {
    private AdapterJadwalSiswa adapterJadwalSiswa;
    private ArrayList<JadwalSiswa> filterList;

    public FilterJadwalSiswa(ArrayList<JadwalSiswa> filterList, AdapterJadwalSiswa adapterJadwalSiswa)
    {
        this.adapterJadwalSiswa=adapterJadwalSiswa;
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
            ArrayList<JadwalSiswa> filteredJadwal=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getHari().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredJadwal.add(filterList.get(i));
                }
            }

            results.count=filteredJadwal.size();
            results.values=filteredJadwal;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterJadwalSiswa.jadwal= (ArrayList<JadwalSiswa>) results.values;

        //REFRESH
        adapterJadwalSiswa.notifyDataSetChanged();

    }
}
