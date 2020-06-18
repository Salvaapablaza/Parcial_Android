package com.example.parcialdispositvos.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.parcialdispositvos.DataBase.DataBase
import com.example.parcialdispositvos.DataBase.Login.UserLoginDao
import com.example.parcialdispositvos.Entities.Login.User
import com.example.parcialdispositvos.R
import com.google.android.material.snackbar.Snackbar

import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    lateinit var v : View


    lateinit var edt_User : EditText
    lateinit var edt_Email : EditText
    lateinit var edt_Password : EditText
    lateinit var edt_Password2: EditText
    lateinit var btn_Registrarse : Button
    lateinit var btn_Regresar : Button

    private var cuadb : DataBase? = null
    private var userDao: UserLoginDao? = null

    var existingUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_register, container, false)

        edt_User = v.findViewById(R.id.edt_User)
        edt_Email = v.findViewById(R.id.edt_Email)
        edt_Password = v.findViewById(R.id.edt_Password)
        edt_Password2 = v.findViewById(R.id.edt_Password2)
        btn_Registrarse = v.findViewById(R.id.btn_Registrar)
        btn_Regresar = v.findViewById(R.id.btn_Regresar)

        return v
    }

    override fun onStart() {
        super.onStart()
        cuadb = DataBase.getDataBase( v.context )
        userDao = cuadb?.userLoginDao()

        btn_Registrarse.setOnClickListener {

            if( (edt_User.length() > 0) && (edt_Email.length() > 0) && (edt_Password.length() > 0) ) {

                if (edt_Password.text.toString() == edt_Password2.text.toString())
                {
                existingUser = userDao?.loadUserByName( edt_User.text.toString() )
                if( existingUser == null ) {
                    existingUser = userDao?.loadUserByEmail( edt_Email.text.toString() )
                    if( existingUser == null ) {
                        var uniqueID = Random().nextInt(1000 )
                        existingUser = userDao?.loadUserById( uniqueID )
                        while ( existingUser != null ) {
                            existingUser = userDao?.loadUserById(uniqueID)
                        }
                        userDao?.insertUser( User(uniqueID, edt_User.text.toString(), edt_Email.text.toString(), edt_Password.text.toString() ) )
                        var actionCreateToLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        v.findNavController().navigate(actionCreateToLogin)
                    }
                    else {
                        Snackbar.make( v, "El mail ya existe. Ingrese otro", Snackbar.LENGTH_SHORT ).show()
                    }
                }
                else {
                    Snackbar.make( v, "El nombre de usuario ya existe. Elija otro", Snackbar.LENGTH_SHORT ).show()
                }
            }
            else{
                Snackbar.make( v, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT ).show()
            }
        }
            else{
                Snackbar.make( v, "Por favor, complete todos los campos", Snackbar.LENGTH_SHORT ).show()
            }
        }


        btn_Regresar.setOnClickListener()
        {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            v.findNavController().navigate(action)
        }


}

}
