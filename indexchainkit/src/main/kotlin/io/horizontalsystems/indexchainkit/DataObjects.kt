package io.horizontalsystems.indexchainkit

import androidx.room.Embedded
import io.horizontalsystems.bitcoincore.extensions.toHexString
import io.horizontalsystems.bitcoincore.models.*
import io.horizontalsystems.bitcoincore.serializers.TransactionSerializer
import io.horizontalsystems.bitcoincore.storage.BlockHeader
import io.horizontalsystems.bitcoincore.utils.HashUtils

class IndexBlockHeader(
        version: Int,
        previousBlockHeaderHash: ByteArray,
        merkleRoot: ByteArray,
        timestamp: Long,
        bits: Long,
        nonce: Long,
        val signature: ByteArray?,
        hash: ByteArray) : BlockHeader(version, previousBlockHeaderHash, merkleRoot, timestamp, bits, nonce, hash)



