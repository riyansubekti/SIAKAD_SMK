package com.smk.siakad.filter;

import android.widget.Filter;

import com.smk.siakad.adapter.AdapterNilaiSiswa;
import com.smk.siakad.model.Nilai;

import java.util.ArrayList;

public class FilterNilaiSiswa extends Filter {
    private AdapterNilaiSiswa adapterNilaiSiswa;
    private ArrayList<Nilai> filterList;

    public FilterNilaiSiswa(ArrayList<Nilai> filterList,AdapterNilaiSiswa adapterNilaiSiswa)
    {
        this.adapterNilaiSiswa=adapterNilaiSiswa;
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
            ArrayList<Nilai> filteredNilai=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getSemester().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredNilai.add(filterList.get(i));
                }
            }

            results.count=filteredNilai.size();
            results.values=filteredNilai;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterNilaiSiswa.nilai= (ArrayList<Nilai>) results.values;

        //REFRESH
        adapterNilaiSiswa.notifyDataSetChanged();

    }
}
