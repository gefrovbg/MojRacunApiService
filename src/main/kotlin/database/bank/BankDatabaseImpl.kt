package database.bank

import database.data.BankEntity
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import tools.transactionCustom

@Service
class BankDatabaseImpl: BankDatabase {

    override fun saveImageUrl(url: String) {
        transactionCustom {
            val bankList = BankEntity.all()
            if (!bankList.empty()){
                bankList.forEach {
                    if (it.imageUrl.isNotBlank()){
                        if (it.imageUrl.contains('=')){
                            val fileName = it.imageUrl.split('=')[1]
                            it.imageUrl = "$url/images/get?fileName=$fileName"
                        }
                    }
                }
            }
        }
    }
}