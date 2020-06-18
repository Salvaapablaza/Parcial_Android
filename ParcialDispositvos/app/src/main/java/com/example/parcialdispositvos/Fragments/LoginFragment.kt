package com.example.parcialdispositvos.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.parcialdispositvos.Activity.Main2Activity
import com.example.parcialdispositvos.DataBase.DataBase
import com.example.parcialdispositvos.DataBase.Login.UserLoginDao
import com.example.parcialdispositvos.Entities.Login.User
import com.example.parcialdispositvos.Activity.MainActivity
import com.example.parcialdispositvos.R
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var v: View

    lateinit var txt_TitleLogin : TextView
    lateinit var edt_User : EditText
    lateinit var edt_Password : EditText
    lateinit var btn_Ingresar : Button
    lateinit var btn_Registrar : Button
    lateinit var btn_IngresoGoogle : Button

    //Db
    private var db: DataBase? = null
    private var UserLoginDao: UserLoginDao? = null


    var actualUser: User? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

       btn_Ingresar = v.findViewById(R.id.btn_Ingresar)
        txt_TitleLogin = v.findViewById(R.id.txt_TitleLogin)
        edt_User = v.findViewById(R.id.edt_User)
        edt_Password = v.findViewById(R.id.edt_Password)
        btn_Registrar = v.findViewById(R.id.btn_Registrar)
        btn_IngresoGoogle = v.findViewById(R.id.btn_IngresoGoogle)
        return v
    }


    override fun onStart() {
        super.onStart()
        db = DataBase.getDataBase(v.context)
        UserLoginDao = db?.userLoginDao()

        btn_Ingresar.setOnClickListener {
            if( (edt_User.length() > 0) && (edt_Password.length() > 0) ) {
                actualUser = UserLoginDao?.loadUserByName( edt_User.text.toString() )
                if( actualUser != null ){
                    if( actualUser?.pass.equals( edt_Password.text.toString() ) ) {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.putExtra("actualUserName" , actualUser?.name )
                        activity?.startActivity(intent)
                        activity?.finish()
                    }
                    else {
                        Snackbar.make( v, "La contraseña es incorrecta", Snackbar.LENGTH_SHORT ).show()
                    }
                }
                else {
                    Snackbar.make( v, "El usuario es incorrecto", Snackbar.LENGTH_SHORT ).show()
                }
            }
            else {
                Snackbar.make( v, "La contraseña o el mail estan incompletos", Snackbar.LENGTH_SHORT ).show()
            }
        }
        btn_Registrar.setOnClickListener {

            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            v.findNavController().navigate(action)

        }
    }
}