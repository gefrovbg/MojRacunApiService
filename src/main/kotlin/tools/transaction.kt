package tools

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun Transaction.myCustomTransactionFunction() {
    addLogger(StdOutSqlLogger)
}

fun <T> transactionCustom(statement: Transaction.() -> T): T {
    return transaction {
        defaultLogger()
        statement()
    }
}

private fun Transaction.defaultLogger() {
    addLogger(StdOutSqlLogger)
}
