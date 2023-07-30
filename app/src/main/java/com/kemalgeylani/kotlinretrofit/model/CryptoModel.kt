package com.kemalgeylani.kotlinretrofit.model

import java.util.Currency

data class CryptoModel(val currency : String, val price : String)
    // data class'larda genellikle, constructor da verileri çekeceğimiz parametreler bulunur.
    // çok fazla metodlar vs olmaz.
