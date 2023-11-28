package security.service

import database.data.User
import database.data.UserDto
import database.data.UserEntity
import database.data.Users
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import tools.transactionCustom
import kotlin.random.Random

@Service
class UserService(
    private val hashService: HashService
) {
    fun findById(id: Long): User? {
        return transaction {
            addLogger(StdOutSqlLogger)
            return@transaction UserEntity.findById(id = id.toInt())?.toUser()
        }
    }

    fun findByName(name: String): User? {
        return try {
            transactionCustom {
                return@transactionCustom UserEntity.find {
                    Users.mail eq name
                }.first().toUser()
            }
        } catch (e: Exception){
            null
        }
    }

    fun existsByName(name: String): Boolean {
        return try {
            transactionCustom {
                return@transactionCustom UserEntity.find {
                    Users.mail eq name
                }.empty()
            }
        } catch (e: Exception){
            false
        }
    }

    fun save(user: UserDto): User {
        return transactionCustom {
            return@transactionCustom UserEntity.new {
                mail = user.mail
                password = user.password
            }.toUser()
        }
    }

    fun saveCode(name: String): String?{
        return try {
            transactionCustom {
                val user = UserEntity.find {
                    Users.mail eq name
                }.first()
                val code = Random.nextInt(1000, 10000).toString()
                user.code = hashService.hashBcrypt(code)
                return@transactionCustom code
            }
        } catch (e: Exception){
            null
        }
    }

    fun clearCode(name: String): Boolean{
        return try {
            transactionCustom {
                val user = UserEntity.find {
                    Users.mail eq name
                }.first()
                user.code = null
                return@transactionCustom true
            }
        } catch (e: Exception){
            false
        }
    }

    fun newPassword(_user: UserDto): Boolean{
        return try {
            transactionCustom {
                val user = UserEntity.find {
                    Users.mail eq _user.mail
                }.first()
                user.code = null
                user.password = _user.password
                return@transactionCustom true
            }
        } catch (e: Exception){
            false
        }
    }
}
