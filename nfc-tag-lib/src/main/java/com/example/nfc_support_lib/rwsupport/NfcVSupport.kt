package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.NfcV
import com.example.nfc_support_lib.util.encodeToHexStr

class NfcVSupport(val nfcV: NfcV): NfcInterfaceSupport {
    override fun connect() = nfcV.connect()

    override fun disConnect() = nfcV.close()

    override fun isConnect(): Boolean = nfcV.isConnected

    override fun getID(): String = nfcV.tag.id.encodeToHexStr()

    override fun getTechs(): Array<out String> = nfcV.tag.techList
}