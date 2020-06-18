package com.example.parcialdispositvos.Fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.parcialdispositvos.DataBase.DataBase
import com.example.parcialdispositvos.DataBase.Mascotas.MascotaDao
import com.example.parcialdispositvos.Entities.Mascotas.Mascota
import com.example.parcialdispositvos.Apis.send_message

import com.example.parcialdispositvos.R

class DetalleMascota : Fragment() {

    lateinit var MascotaDetalleView : View

    private var dbmascota : DataBase? = null
    private var mascotaDao : MascotaDao? = null

    lateinit var img_raza_actual : ImageView

    lateinit var edt_nombre : EditText
    lateinit var edt_raza : EditText
    lateinit var edt_edad : EditText

    lateinit var btn_Modificar : Button
    lateinit var btn_Regresar : Button
    var identifier: String? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MascotaDetalleView = inflater.inflate(R.layout.fragment_detalle_mascota, container, false)

        edt_nombre = MascotaDetalleView.findViewById(R.id.edt_nombredet)
        edt_raza = MascotaDetalleView.findViewById(R.id.edt_razadet)
        edt_edad = MascotaDetalleView.findViewById(R.id.edt_edaddet)
        btn_Modificar = MascotaDetalleView.findViewById(R.id.btn_det_Modificar)
        btn_Regresar = MascotaDetalleView.findViewById(R.id.btn_cat_Regresar)
        img_raza_actual = MascotaDetalleView.findViewById(R.id.img_CatActual)



        return MascotaDetalleView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        dbmascota = DataBase.getDataBase(MascotaDetalleView.context)
        mascotaDao = dbmascota?.MascotaDao()
        identifier = DetalleMascotaArgs.fromBundle(requireArguments()).identifier
        when(item.itemId) {

            R.id.action_editar -> {
                edt_raza.isEnabled = true
                edt_edad.isEnabled = true
                edt_nombre.isEnabled = true
                btn_Modificar.visibility = View.VISIBLE
            }
            R.id.action_eliminar -> {
                val builder = AlertDialog.Builder(MascotaDetalleView.context)
                builder.setMessage("Esta seguro que desea eliminar la mascota?")
                    .setCancelable(false)
                    .setPositiveButton("Si") { _, _ ->

                        mascotaDao?.deleteMascota(mascotaDao?.loadMascotabyid(identifier!!))
                        send_message(
                            this.MascotaDetalleView,
                            "Mascota  eliminada"
                        )
                        btn_Regresar.callOnClick()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        identifier = DetalleMascotaArgs.fromBundle(requireArguments()).identifier
        dbmascota = DataBase.getDataBase(MascotaDetalleView.context)
        mascotaDao = dbmascota?.MascotaDao()

        val mascotactual : Mascota? = mascotaDao?.loadMascotabyid(identifier!!)

        edt_nombre.setText(mascotactual?.nombre)
        edt_nombre.isEnabled = false
        edt_raza.setText(mascotactual?.raza)
        edt_raza.isEnabled = false
        edt_edad.setText(mascotactual?.edad.toString())
        edt_edad.isEnabled = false
        btn_Modificar.visibility = View.INVISIBLE


        when(mascotactual?.raza.toString()){
            "Perro"-> {
                img_raza_actual.setImageResource(R.mipmap.mila)

            }
            "Boxer"-> {
                img_raza_actual.setImageResource(R.mipmap.mila)

            }
            "Labrador"-> {
                img_raza_actual.setImageResource(R.mipmap.labrador)

            }
            "Gato"-> {
                img_raza_actual.setImageResource(R.mipmap.gato)

            }
            "Pastor"-> {
                img_raza_actual.setImageResource(R.mipmap.pastor)

            }
            else-> img_raza_actual.setImageResource(R.mipmap.otras)
        }


        btn_Regresar.setOnClickListener{
            setHasOptionsMenu(false)
            val action =DetalleMascotaDirections.actionDetalleMascotaToListFragment()
           MascotaDetalleView.findNavController().navigate(action)
        }

        btn_Modificar.setOnClickListener{
            val newMascota = Mascota(
                identifier!! ,edt_edad.text.toString().toInt(),edt_nombre.text.toString(),edt_raza.text.toString(),mascotactual!!.nombredueno)
            mascotaDao?.updateMascota(newMascota)


            send_message(this.MascotaDetalleView, "Mascota modificada")
            btn_Regresar.callOnClick()
        }
    }


}
