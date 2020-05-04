package com.example.coach.vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.coach.R;
import com.example.coach.controleur.Control;
import com.example.coach.modele.Profil;
import com.example.coach.outils.MesOutils;

import java.util.ArrayList;

public class HistoListAdapter extends BaseAdapter {

    private ArrayList<Profil> lesProfils;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public HistoListAdapter(Context context, ArrayList<Profil> lesProfils){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesProfils = lesProfils;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesProfils.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesProfils.get(position);
    }

    /**
     * retourne un indice par rapport a la ligne actuel
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * retourne la ligne (view) formater avec gestion des evenements
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // declaration d'un holder
        ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            holder = new ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_histo, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListDate = (TextView)view.findViewById(R.id.txtlistDate);
            holder.btnListSuppr = (ImageButton)view.findViewById(R.id.btnListSuppr);
            holder.txtListIMG = (TextView)view.findViewById(R.id.txtlistIMG);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        holder.txtListDate.setText(MesOutils.convertDateToString(lesProfils.get(position).getDateMesure()));
        holder.txtListIMG.setText(MesOutils.format2Deciaml(lesProfils.get(position).getImg()));
        holder.btnListSuppr.setTag(position);
        // click sur la croix pour supprimer le profil enregistrer
        holder.btnListSuppr.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    // demande la suppresion au controleur
                    control.delProfil(lesProfils.get(position));
                    // rafraichir la liste
                    notifyDataSetChanged();
                }
            });

        holder.txtListDate.setTag(position);
        // click sur le reste de la ligne pour afficher le profil dans calcul activity
        holder.txtListDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // demande de l'affichage du profil dans calculActivity
                ((HistoActivity)context).afficheProfil(lesProfils.get(position));
            }
        });

        holder.txtListIMG.setTag(position);
        // click sur le reste de la ligne pour afficher le profil dans calcul activity
        holder.txtListIMG.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // demande de l'affichage du profil dans calculActivity
                ((HistoActivity)context).afficheProfil(lesProfils.get(position));
            }
        });

        return view;
    }

    private class ViewHolder{
        ImageButton btnListSuppr;
        TextView txtListDate;
        TextView txtListIMG;
    }
}
