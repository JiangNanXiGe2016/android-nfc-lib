package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.IsoDep
import android.util.Log

import com.example.nfc_support_lib.util.checkResult
import com.example.nfc_support_lib.util.decodeHexStrToArray
import com.example.nfc_support_lib.util.encodeToHexStr
import java.util.Locale

class IsoDepSupport(val isoDep: IsoDep) : NfcInterfaceSupport {
    var name: String? = null
    var id: String? = null
    var serl: String? = null
    var version: String? = null
    var date: String? = null
    var count: String? = null
    var cash: String? = null
    var log: String? = null
    var stringBuffer: StringBuffer = StringBuffer()
    private fun info() {


    }

    private fun history() {

    }

    private fun log() {

    }

    fun parseCard() {
        //select Main Application
        connect()
        val id = getID()
        Log.i("IsoDepCard", "id=$id")
        val techs: Array<String> = getTechs()
        Log.i("IsoDepCard", "techs=${techs.contentToString()}")
        selectApp()
        getAllAids()
        verify()
        initPurchase()
        getBalance(true)
        disConnect()
    }

    /**
     * 获取所有的aid
     * **/
    private fun getAllAids() {

    }

    override fun connect() = isoDep.connect()
    override fun disConnect() = isoDep.close()

    override fun isConnect() = isoDep.isConnected
    override fun getID() = isoDep.tag.id.encodeToHexStr()
    override fun getTechs(): Array<String> = isoDep.tag.techList


    // command verify
    private fun verify() {
        val cmd = byteArrayOf(
            0x00.toByte(),
            0x20.toByte(),
            0x00.toByte(),
            0x00.toByte(),
            0x02.toByte(),
            0x12.toByte(),
            0x34.toByte()
        )

        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " verify  data=${str}  $error")
    }

    // command initPurchase
    private fun initPurchase(isEP: Boolean = false) {

        val cmd = byteArrayOf(
            0x80.toByte(),
            0x50.toByte(),
            0x01.toByte(),
            (if (isEP) 2 else 1).toByte(),
            0x0B.toByte(),
            0x01.toByte(),
            0x00.toByte(),
            0x00.toByte(),
            0x00.toByte(),
            0x00.toByte(),
            0x11.toByte(),
            0x22.toByte(),
            0x33.toByte(),
            0x44.toByte(),
            0x55.toByte(),
            0x66.toByte(),
            0x0F.toByte()
        )

        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " initPurchase  data=${str}  $error")

    }

    // command getBalance
    private fun getBalance(isEP: Boolean = false) {
        val cmd = byteArrayOf(
            0x80.toByte(),
            0x5C.toByte(),
            0x00.toByte(),
            (if (isEP) 2 else 1).toByte(),
            0x04.toByte()
        )
        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " getBalance  data=${str}  $error")


    }

    // command readRecord
    private fun readRecord(sfi: Int, index: Int) {
        val cmd = byteArrayOf(
            0x00.toByte(),
            0xB2.toByte(),
            index.toByte(),
            (sfi shl 3 or 0x04).toByte(),
            0x00.toByte()
        )

        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " readRecord  data=${str}  $error")

    }

    private fun readRecord(sfi: Int) {
        val cmd = byteArrayOf(
            0x00.toByte(), 0xB2.toByte(), 0x01.toByte(), (sfi shl 3 or 0x05).toByte(), 0x00.toByte()
        )
        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " readRecord  data=${str}  $error")

    }

    private fun readBinary(sfi: Int) {
        val cmd = byteArrayOf(
            0x00.toByte(),// CLA Class
            0xB0.toByte(),// INS Instruction
            (0x00000080 or (sfi and 0x1F)).toByte(),// P1 Parameter 1
            0x00.toByte(),// P2 Parameter 2
            0x00.toByte() // Le
        )
        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
        var ret: ByteArray = trans(cmd)
        var str = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(str)
        Log.i("IsoDepCard", " readBinary  data=${str}  $error")

    }

    private fun readData(sfi: Int) {}

    /**
     * CLA INS P1 P2 LC Data
     *
     *
     *@see<a href="https://blog.csdn.net/pingqingbo/article/details/12658161"></a>
     *
     *
     * ***/
    private fun selectApp() {
        // 如果已知卡内应用aid则此处需要替换
        var app = "A0000002471001"
        var name: ByteArray = app.decodeHexStrToArray()
        var command: ByteArray = ByteArray(5)
        command[0] = 0x00.toByte() // CLA Class
        command[1] = 0xA4.toByte()// INS Instruction
        command[2] = 0x04.toByte()// P1  Parameter 1
        command[3] = 0x00.toByte()// P2  Parameter 2
        command[4] = 0x07.toByte() //size
        command += name
        Log.i("IsoDepCard", "cmd =${command.encodeToHexStr()}")
        if (!isConnect()) {
            Log.i("IsoDepCard", "connect fail")
            return
        }
//        var origin = "00A4040007A0000002471001"
//        var temp: ByteArray = origin.decodeHex()
        var ret: ByteArray = trans(command)
        var retStr = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error = checkResult(retStr)
        Log.i("IsoDepCard", " selectByName cmd=${command.encodeToHexStr()}  ret=${retStr}  $error")

    }


    private fun trans(cmd: ByteArray): ByteArray = isoDep.transceive(cmd)

}