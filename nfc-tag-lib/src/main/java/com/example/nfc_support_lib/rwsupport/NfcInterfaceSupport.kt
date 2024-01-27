package com.example.nfc_support_lib.rwsupport


interface NfcInterfaceSupport {

    fun connect()
    fun disConnect()
    fun isConnect(): Boolean
    fun getID(): String
    fun getTechs(): Array<out String>

    companion object {
        val tag: String = "NfcBaseCard"
    }
}