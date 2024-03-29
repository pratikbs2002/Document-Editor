package com.example.navigationbottompractice.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationbottompractice.R;
import com.example.navigationbottompractice.tools.merge_pdf;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragment extends Fragment {
CardView merge_pdf,split_pdf,signature,watermark,compress_pdf,qr_scanner,jpg_to_pdf,word_to_pdf,ppt_to_pdf,pdf_to_jpg,pdf_to_word,pdf_to_ppt;
Toolbar toolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(String param1, String param2) {
        homefragment fragment = new homefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_homefragment, container, false);

        merge_pdf = (CardView) v.findViewById(R.id.merge_pdf_button);
        split_pdf = (CardView) v.findViewById(R.id.split_pdf_button);
        signature = (CardView) v.findViewById(R.id.signature);
        watermark = (CardView) v.findViewById(R.id.watermark);
        compress_pdf = (CardView) v.findViewById(R.id.compress_pdf);
        qr_scanner = (CardView) v.findViewById(R.id.qr_scanner);
        jpg_to_pdf= (CardView) v.findViewById(R.id.jpg_to_pdf);
        word_to_pdf = (CardView) v.findViewById(R.id.word_to_pdf);
        ppt_to_pdf = (CardView) v.findViewById(R.id.ppt_to_pdf);
        pdf_to_jpg= (CardView) v.findViewById(R.id.pdf_to_jpg);
        pdf_to_word = (CardView) v.findViewById(R.id.pdf_to_word);
        pdf_to_ppt = (CardView) v.findViewById(R.id.pdf_to_ppt);


        split_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.split_pdf.class);
                startActivity(intent);
            }
        });

        merge_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), merge_pdf.class);
                startActivity(intent);
            }
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.signature.class);
                startActivity(intent);
            }
        });

        watermark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.watermark.class);
                startActivity(intent);
            }
        });

        compress_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.compress_pdf.class);
                startActivity(intent);
            }
        });

        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.qr_scanner.class);
                startActivity(intent);
            }
        });

        jpg_to_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.jpg_to_pdf.class);
                startActivity(intent);
            }
        });

        word_to_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.word_to_pdf.class);
                startActivity(intent);
            }
        });

        ppt_to_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.ppt_to_pdf.class);
                startActivity(intent);
            }
        });

        pdf_to_jpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.pdf_to_jpg.class);
                startActivity(intent);
            }
        });

        pdf_to_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.pdf_to_word.class);
                startActivity(intent);
            }
        });

        pdf_to_ppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.navigationbottompractice.tools.pdf_to_ppt.class);
                startActivity(intent);
            }
        });


        return v;
    }
}