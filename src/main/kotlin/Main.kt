package org.ligi.keccakcompare

import com.theromus.sha.Keccak
import com.theromus.sha.Parameters
import org.walleth.khex.hexToByteArray
import org.walleth.khex.toNoPrefixHexString

val testVectors = listOf(
        "".toByteArray(),
        "The quick brown fox jumps over the lazy dog".toByteArray(),
        "The quick brown fox jumps over the lazy dog.".toByteArray(),
        //"The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.".toByteArray(),
        "a1b31be4d58a7ddd24b135db0da56a90fb5382077ae26b250e1dc9cd6232ce2270f4c995428bc76aa78e522316e95d7834d725efc9ca754d043233af6ca90113".hexToByteArray()
)

fun ByteArray.spongyKeccak() = org.spongycastle.jcajce.provider.digest.Keccak.Digest256().let {
    it.update(this)
    it.digest()
}


fun main(args: Array<String>) {

    testVectors.forEachIndexed { index, input ->
        println("testing vector $index")
        val spongyResult =  input.spongyKeccak().toNoPrefixHexString()
        println("spongy: " + spongyResult)

        val romusResult = Keccak().getHash(input, Parameters.KECCAK_256).toNoPrefixHexString()
        println("romus : " + romusResult)


        if (romusResult!=spongyResult) {
            throw RuntimeException("Results do not match")
        }
    }
}