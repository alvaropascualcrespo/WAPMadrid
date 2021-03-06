package com.wapmadrid.miPerfil.cuestionario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wapmadrid.R;
import com.wapmadrid.utilities.Constants;


/**
 * Created by Ismael on 18/05/2015.
 */
public class CuestionarioFragment extends Fragment implements View.OnClickListener{

    private TextView txtPregunta;
    private Button btnRespuesta1;
    private Button btnRespuesta0;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cuestionario_fragment, container, false);

        txtPregunta = (TextView) v.findViewById(R.id.txtPregunta);
        btnRespuesta1 = (Button) v.findViewById(R.id.btnRespuesta1);
        btnRespuesta0 = (Button) v.findViewById(R.id.btnRespuesta0);

        position = getArguments().getInt(Constants.FRAGMENT_POSITION);
        String[] preguntas = getResources().getStringArray(R.array.cuestionario_preguntas);
        String[] respuestas1 = getResources().getStringArray(R.array.cuestionario_respuestas1);
        String[] respuestas0 = getResources().getStringArray(R.array.cuestionario_respuestas0);
        txtPregunta.setText(preguntas[position]);
        btnRespuesta1.setText(respuestas1[position]);
        btnRespuesta0.setText(respuestas0[position]);

        btnRespuesta1.setOnClickListener(this);
        btnRespuesta0.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnRespuesta1.getId()){
            ((CuestionarioActivity)getActivity()).setRespuesta(position,1);
            if (btnRespuesta0.isSelected()){
                btnRespuesta0.setSelected(false);
            }
            btnRespuesta1.setSelected(true);
        } else if (v.getId() == btnRespuesta0.getId()){
            ((CuestionarioActivity)getActivity()).setRespuesta(position,0);
            if (btnRespuesta1.isSelected()){
                btnRespuesta1.setSelected(false);
            }
            btnRespuesta0.setSelected(true);
        }

    }
}
