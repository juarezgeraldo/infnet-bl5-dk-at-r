package com.infnet.juarez.acessoapirest.service

import com.infnet.juarez.acessoapirest.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepAPI {

    @GET("{cep}/json/")
    abstract fun getCep(@Path("cep") cep : String) : Call<Cep>
    }