package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.NfcB
import com.example.nfc_support_lib.util.encodeToHexStr

class NfcBSupport(private val nfcB: NfcB) : NfcInterfaceSupport {
    override fun connect() = nfcB.connect()

    override fun disConnect() = nfcB.close()

    override fun isConnect(): Boolean = nfcB.isConnected

    override fun getID(): String = nfcB.tag.id.encodeToHexStr()

    override fun getTechs(): Array<out String> = nfcB.tag.techList
}