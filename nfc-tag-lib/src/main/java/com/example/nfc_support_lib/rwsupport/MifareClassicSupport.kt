package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.MifareClassic
import android.util.Log
import com.example.nfc_support_lib.rwsupport.NfcInterfaceSupport.Companion.tag
import com.example.nfc_support_lib.util.encodeToHexStr


class MifareClassicSupport(var mifareClassic: MifareClassic) : NfcInterfaceSupport {

    var id: String? = null
    var sectorCount: Int? = null
    var type: Int? = null
    var size: Int? = null
    lateinit var data: ByteArray
    lateinit var firsBlock: ByteArray

    override fun connect() = mifareClassic.connect()

    override fun disConnect() = mifareClassic.close()

    override fun isConnect() = mifareClassic.isConnected

    override fun getID() = mifareClassic.tag.id.encodeToHexStr()

    private fun sectorCount() = mifareClassic.sectorCount

    fun parseCard() {
        connect()
        if (!isConnect()) {
            Log.i(tag, "MifareClassicCard  not connect")
            return
        }

        //获取TAG的类型
        type = getType()
        Log.i(tag, "MifareClassicCard  type=$type")
        size = getSize()
        Log.i(tag, "MifareClassicCard  size=$size")
        id = getID()
        Log.i(tag, "MifareClassicCard  id=$id")

        sectorCount = sectorCount()
        Log.i(tag, "MifareClassicCard  sectorCount=$sectorCount")

        //data
        data = allSectorsBlockData()
        var list: List<String> = data.encodeToHexStr().chunked(32)
        var sb = StringBuffer()
        list.map {
            sb.append(it)
            sb.append("\n")
        }
        Log.i(tag, "MifareClassicCard  data=$sb")
        firsBlock = readFirstSectorData()
        var firstSectorList: List<String> = firsBlock.encodeToHexStr().chunked(32)
        var sbff = StringBuffer()
        firstSectorList.map {
            sbff.append(it)
            sbff.append("\n")
        }
        Log.i(tag, "MifareClassicCard  firsBlock=$sbff")
        mifareClassic.close()
    }


    /**
     * */


    private fun readFirstSectorData(): ByteArray {
        var data: ByteArray = ByteArray(0)
        var auth: Boolean = mifareClassic.authenticateSectorWithKeyA(0, MifareClassic.KEY_DEFAULT);
        if (auth) {
            var bCount: Int = mifareClassic.getBlockCountInSector(0)
            var sectorIndex = mifareClassic.sectorToBlock(0);
            for (j in 0 until bCount) {
                var block: ByteArray = mifareClassic.readBlock(j);
                data += block
            }
        } else {
            Log.i(tag, "MifareClassicCard  auth=$auth")
        }
        return data

    }

    /**
     * 读取所有扇区的数据
     * */
    private fun allSectorsBlockData(): ByteArray {
        var data: ByteArray = ByteArray(0)
        var sectorCount = mifareClassic.sectorCount
        for (i in 0 until sectorCount) {
            try {
                var auth: Boolean =
                    mifareClassic.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);
                if (auth) {
                    var bCount: Int = mifareClassic.getBlockCountInSector(i)
                    var sectorIndex = mifareClassic.sectorToBlock(i);
                    for (j in 0 until bCount) {
                        var block: ByteArray = mifareClassic.readBlock(i * bCount + j);
                        data += block
                    }
                } else {
                    Log.i(tag, "MifareClassicCard  auth=$auth")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(tag, "MifareClassicCard  sector index==$i")
            }
        }


        return data
    }

    private fun getSize(): Int {
        val size: Int = mifareClassic.size
        return size
    }

    private fun getType(): Int {
        val type: Int = mifareClassic.type
        var typeStr: String = when (type) {
            MifareClassic.TYPE_CLASSIC -> "TYPE_CLASSIC"
            MifareClassic.TYPE_PLUS -> "TYPE_PLUS"
            MifareClassic.TYPE_PRO -> "TYPE_PRO"
            MifareClassic.TYPE_UNKNOWN -> "TYPE_UNKNOWN"
            else -> "null"
        }
        Log.i(tag, "MifareClassicCard  typeStr=$typeStr")
        return type
    }

    override fun getTechs(): Array<out String> =mifareClassic.tag.techList
}
