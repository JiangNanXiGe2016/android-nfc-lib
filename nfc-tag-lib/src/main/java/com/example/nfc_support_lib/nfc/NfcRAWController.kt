package com.example.nfc_support_lib.nfc

import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcA
import android.util.Log
import com.example.nfc_support_lib.rwsupport.IsoDepSupport
import com.example.nfc_support_lib.rwsupport.MifareClassicSupport
import com.example.nfc_support_lib.rwsupport.NdefFormatAbleSupport
import com.example.nfc_support_lib.rwsupport.NfcASupport

class NfcRAWController {
    //TAG: Tech [android.nfc.tech.NfcA, android.nfc.tech.MifareClassic, android.nfc.tech.NdefFormatable]
    private val TAG: String = "NfcCardController"
    fun readCardInfo(tag: Tag) {
        var nfcA: NfcA? = NfcA.get(tag)
        if (nfcA != null) {
            var nfcACard = NfcASupport(nfcA)
            nfcACard?.parseCard()
            return
        }

        var isoDep: IsoDep? = IsoDep.get(tag)
        if (isoDep != null) {
            Log.i(TAG, "isoDep=$isoDep")
            var isoDepCard: IsoDepSupport = IsoDepSupport(isoDep)
            isoDepCard.parseCard()
            return
        }

        var mifareClassic: MifareClassic? = MifareClassic.get(tag)
        if (mifareClassic != null) {
            Log.i(TAG, "mifareClassic=$mifareClassic")
            var mifareClassicCard = MifareClassicSupport(mifareClassic)
            mifareClassicCard.parseCard()
            return
        }
        var ndefFormatable: NdefFormatable? = NdefFormatable.get(tag)
        if (ndefFormatable != null) {
            Log.i(TAG, "ndefFormatable=$ndefFormatable")
            var nfcformable = NdefFormatAbleSupport(ndefFormatable)
            nfcformable.parseCard()
            return
        }
//        var nfcB: NfcB? = NfcB.get(tag)
//        if (nfcA != null) {
//            Log.i(TAG, "nfcB=$nfcB")
//            return
//        }
//
//        var nfcV: NfcV? = NfcV.get(tag)
//        if (nfcA != null) {
//            Log.i(TAG, "nfcV=$nfcV")
//            return
//        }
//
//
//        var nfcF: NfcF? = NfcF.get(tag)
//        if (nfcA != null) {
//            Log.i(TAG, "nfcF=$nfcF")
//            return
//        }


//        var ndeF: Ndef? = Ndef.get(tag)
//        if (nfcA != null) {
//            Log.i(TAG, "ndeF=$ndeF")
//            return
//        }
//
//
//        var mifareUltralight: MifareUltralight? = MifareUltralight.get(tag)
//        if (nfcA != null) {
//            Log.i(TAG, "mifareUltralight=$mifareUltralight")
//            return
//        }

    }


}