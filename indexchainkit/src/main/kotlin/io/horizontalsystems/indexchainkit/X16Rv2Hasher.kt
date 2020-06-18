package io.horizontalsystems.indexchainkit

import fr.cryptohash.*
import io.horizontalsystems.bitcoincore.core.IHasher
import java.util.*

class TigerKeccak512 : Digest {
    val tiger = Tiger()
    val keccak512 = Keccak512()
    override fun getDigestLength(): Int {
        return keccak512.digestLength
    }

    override fun update(`in`: Byte) {
        tiger.update(`in`)
    }

    override fun update(inbuf: ByteArray?) {
        tiger.update(inbuf)
    }

    override fun update(inbuf: ByteArray?, off: Int, len: Int) {
        tiger.update(inbuf, off, len)
    }

    override fun digest(): ByteArray {
        val hash = tiger.digest();
        return keccak512.digest(hash)
    }

    override fun digest(inbuf: ByteArray): ByteArray {
        var hash = tiger.digest(inbuf)
        return keccak512.digest(hash)
    }

    override fun digest(outbuf: ByteArray?, off: Int, len: Int): Int {
        val hash = tiger.digest()
        keccak512.update(hash)
        return keccak512.digest(outbuf, off, len)
    }

    override fun reset() {
        tiger.reset()
        keccak512.reset()
    }

    override fun copy(): Digest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBlockLength(): Int {
        return tiger.blockLength
    }
}

class TigerLuffa512 : Digest {
    val tiger = Tiger()
    val luffa512 = Luffa512()
    override fun getDigestLength(): Int {
        return luffa512.digestLength
    }

    override fun update(`in`: Byte) {
        tiger.update(`in`)
    }

    override fun update(inbuf: ByteArray?) {
        tiger.update(inbuf)
    }

    override fun update(inbuf: ByteArray?, off: Int, len: Int) {
        tiger.update(inbuf, off, len)
    }

    override fun digest(): ByteArray {
        val hash = tiger.digest();
        return luffa512.digest(hash)
    }

    override fun digest(inbuf: ByteArray): ByteArray {
        var hash = tiger.digest(inbuf)
        return luffa512.digest(hash)
    }

    override fun digest(outbuf: ByteArray?, off: Int, len: Int): Int {
        val hash = tiger.digest()
        luffa512.update(hash)
        return luffa512.digest(outbuf, off, len)
    }

    override fun reset() {
        tiger.reset()
        luffa512.reset()
    }

    override fun copy(): Digest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBlockLength(): Int {
        return tiger.blockLength
    }
}

class TigerSHA512 : Digest {
    val tiger = Tiger()
    val sha512 = SHA512()
    override fun getDigestLength(): Int {
        return sha512.digestLength
    }

    override fun update(`in`: Byte) {
        tiger.update(`in`)
    }

    override fun update(inbuf: ByteArray?) {
        tiger.update(inbuf)
    }

    override fun update(inbuf: ByteArray?, off: Int, len: Int) {
        tiger.update(inbuf, off, len)
    }

    override fun digest(): ByteArray {
        val hash = tiger.digest();
        return sha512.digest(hash)
    }

    override fun digest(inbuf: ByteArray): ByteArray {
        var hash = tiger.digest(inbuf)
        return sha512.digest(hash)
    }

    override fun digest(outbuf: ByteArray?, off: Int, len: Int): Int {
        val hash = tiger.digest()
        sha512.update(hash)
        return sha512.digest(outbuf, off, len)
    }

    override fun reset() {
        tiger.reset()
        sha512.reset()
    }

    override fun copy(): Digest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBlockLength(): Int {
        return tiger.blockLength
    }
}

class X16Rv2Hasher : IHasher {
    private val algorithms = listOf(
            BLAKE512(),
            BMW512(),
            Groestl512(),
            JH512(),
            TigerKeccak512(),
            Skein512(),
            TigerLuffa512(),
            CubeHash512(),
            SHAvite512(),
            SIMD512(),
            ECHO512(),
            Hamsi512(),
            Fugue512(),
            Shabal512(),
            Whirlpool(),
            TigerSHA512()
    )
    fun getNibble(data: ByteArray, index: Int): Int
    {
        var index = 63 - index;
        if (index % 2 == 1)
            return data[index / 2].toInt() shr  4
        return(data[index / 2].toInt() and  0x0F);
    }
    override fun hash(data: ByteArray): ByteArray {
        var hash = data

        algorithms.forEach {
            hash = it.digest(hash)
        }
        for (i in 0 until 16) {
            val hashSelection = getNibble(hash,48+i)
            hash = algorithms[hashSelection].digest(hash)
        }
        return hash.copyOfRange(0, 32)
    }
}
