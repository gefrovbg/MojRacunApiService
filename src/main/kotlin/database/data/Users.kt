package database.data

import database.data.racun.RacunEntity
import database.data.racun.Racuns
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable(){
    val mail = varchar("mail", 50)
    val password = varchar("password", 100)
    val code = varchar("code", 100).nullable()
}

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var mail by Users.mail
    var password by Users.password
    var code by Users.code

    val racun by RacunEntity referrersOn Racuns.user
//    val seller by RacunEntity referrersOn Racuns.seller

    fun toUser(): User{
        return User(
            mail = mail,
            password = password,
            code = code,
            id = this.id.value
        )
    }
}

open class UserDto(
    val mail: String,
    val password: String,
    val code: String? = null
)

class User(
    val id: Int,
    mail: String,
    password: String,
    code: String? = null
) : UserDto(mail, password, code)