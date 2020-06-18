package com.example.parcialdispositvos.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.parcialdispositvos.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.example.parcialdispositvos.Entities.Mascotas.Mascota
import com.example.parcialdispositvos.Adapters.MascotaListAdapter
import com.example.parcialdispositvos.DataBase.Mascotas.MascotaDao
import com.example.parcialdispositvos.DataBase.DataBase


class listFragment : Fragment() {

    lateinit var v: View

    lateinit var btnAdd : FloatingActionButton

    lateinit var recMascotas : RecyclerView
    //Database
    private var ctdb: DataBase? = null
    private var ctcharacterDao: MascotaDao? = null

    var actualUser: String? = null

    var listMascotas : MutableList<Mascota>? = null
    //var mascotaprueba= Mascota(identifier = "salvamila",edad = 3,nombre = "Mila",raza = "boxer",nombredueno = "pepe")
    //var mascotaprueba2= Mascota(identifier = "salvaPaddy",edad = 3,nombre = "curuncho",raza = "boxer",nombredueno = "josee")

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mascotasListAdapter: MascotaListAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_list, container, false)
        setHasOptionsMenu(true)
        btnAdd = v.findViewById(R.id.btn_add)
        recMascotas = v.findViewById(R.id.rec_mascotas)


        return v
    }


    override fun onStart() {
        super.onStart()
      //  ctcharacterDao?.insertMascota(mascotaprueba)
       // ctcharacterDao?.insertMascota(mascotaprueba2)
        actualUser = activity?.intent?.getStringExtra( "actualUserName" )

        btnAdd.setOnClickListener {
            var action = listFragmentDirections.actionListFragmentToAddMasc(actualUser!!)
            v.findNavController().navigate(action)
        }

        ctdb = DataBase.getDataBase(v.context)
        ctcharacterDao = ctdb?.MascotaDao()

        listMascotas = ctcharacterDao?.loadAllMascotasbydueno(actualUser!!) as MutableList<Mascota>

        recMascotas.setHasFixedSize(true)
        recMascotas.adapter?.notifyDataSetChanged()
        //Recycler
        linearLayoutManager = LinearLayoutManager(context)
        recMascotas.layoutManager = linearLayoutManager
        mascotasListAdapter = MascotaListAdapter(listMascotas!!){position: Int ->onItemClick(position)}
        recMascotas.adapter = mascotasListAdapter
    }

    public fun onItemClick (index: Int){
        //Snackbar.make(v,"click", Snackbar.LENGTH_SHORT).show()
        var action = listFragmentDirections.actionListFragmentToDetalleMascota(listMascotas!![index].identifier)
        v.findNavController().navigate(action)
    }

}
