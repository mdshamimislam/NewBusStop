package com.shamim.newbusstop.drawer_layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shamim.newbusstop.Bus_Model_Class;
import com.shamim.newbusstop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Contact_customer extends Fragment {
    ImageSlider mainSlider;
    TextView bus_name_contact1;
    ListView  bus_name_contact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_drawerlayout, container, false);


        mainSlider = view.findViewById(R.id.image_slider);
        bus_name_contact=view.findViewById(R.id.bus_company_name_contact);
        bus_name_contact1=view.findViewById(R.id.bus_company_name_contact1);


        final List<SlideModel> remoteimage = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren())
                        {
                            remoteimage.add(new SlideModel(data.child("profileImageurl").getValue().toString(),data.child("email").getValue().toString(), ScaleTypes.FIT));
                            mainSlider.setImageList(remoteimage,ScaleTypes.FIT);
                            mainSlider.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onItemSelected(int i) {

                                    if (i<3) {
                                        switch (i) {
                                            case 0:
                                                Toast.makeText(getContext(), "Pic1", Toast.LENGTH_SHORT).show();
                                            case 1:
                                                Toast.makeText(getContext(), "Pic2", Toast.LENGTH_SHORT).show();
                                            case 2:
                                                Toast.makeText(getContext(), "Pic3", Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        final List<Bus_Model_Class> check_Bus = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data:snapshot.getChildren())
                        {

                            check_Bus.add(new Bus_Model_Class(data.child("email").getValue().toString(),data.child("phone").getValue().toString(),data.child("bus_name").getValue().toString()));


                            /* Map<String, Object> map = (Map<String, Object>) data.getValue();
                            if(map.get("bus_name") !=null){
                                if (map.get("bus_name").equals("Rida")) {
                                    String bus_name_contact_check = map.get("bus_name").toString();
                                    bus_name_contact.setText(bus_name_contact_check);
                                    String bus_name_contact_check1 = map.get("phone"+""+"email"+""+"phone").toString();
                                    bus_name_contact1.setText(bus_name_contact_check1);
                                }
                                else if (map.get("bus_name").equals("Anabil"))
                                {

                                }
                            }*/




                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;





    }


}
