package com.infnet.juarez.acessoapirest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.infnet.juarez.acessoapirest.model.Cep
import com.infnet.juarez.acessoapirest.service.CepAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var cepAPI: CepAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtLogradouro = this.findViewById<TextView>(R.id.txtLogradouro)
        val txtBairro = this.findViewById<TextView>(R.id.txtBairro)
        val txtUF = this.findViewById<TextView>(R.id.txtUF)
        val txtLocalidade = this.findViewById<TextView>(R.id.txtLocalidade)
        val txtComplemento = this.findViewById<TextView>(R.id.txtComplemento)

        val btnConsulta = this.findViewById<Button>(R.id.btnConsulta)
        btnConsulta.setOnClickListener() {
            val edtCep = this.findViewById<EditText>(R.id.edtCep)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            cepAPI = retrofit.create(CepAPI::class.java)

            val call = cepAPI.getCep(edtCep.text.toString())

            call.enqueue(object : Callback<Cep?> {
                override fun onResponse(call: Call<Cep?>, response: Response<Cep?>) {
                    val cep: Cep? = response.body()
                    if (cep == null){
                        Toast.makeText(this@MainActivity, "Cep não encontrdo.", Toast.LENGTH_LONG).show()
                    }
                    txtLogradouro.setText(cep?.logradouro)
                    txtBairro.setText(cep?.bairro)
                    txtUF.setText(cep?.uf)
                    txtLocalidade.setText(cep?.localidade)
                    txtComplemento.setText(cep?.complemento)
                }
                override fun onFailure(call: Call<Cep?>, t: Throwable) {
                    Log.i("ERRO_CEP", "Erro na busca do cep")
                }
            })
        }
    }
}