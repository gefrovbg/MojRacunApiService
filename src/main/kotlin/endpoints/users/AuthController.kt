package endpoints.users

import database.data.UserDto
import endpoints.dto.ApiException
import endpoints.dto.LoginDto
import endpoints.dto.LoginResponseDto
import endpoints.dto.RegisterDto
import mailclient.EmailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import security.service.HashService
import security.service.TokenService
import security.service.UserService
import java.util.concurrent.CompletableFuture
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * This controller handles login and register requests.
 * Both routes are public as specified in SecurityConfig.
 */
@RestController
@RequestMapping("/api")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
    private val emailService: EmailService
) {

    @PostMapping("/login")
    @Async
    fun login(@RequestBody payload: LoginDto): CompletableFuture<ResponseEntity<String>> {
        val user = userService.findByName(payload.mail) ?: return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"))

        if (payload.password != null && !hashService.checkBcrypt(payload.password, user.password)) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"))
        }

        val code = userService.saveCode(payload.mail)

        if (code != null) {
            CompletableFuture.runAsync {
                emailService.sendHtmlEmail(user.mail, "Verification", "<p>Your code: <b>$code</b></p>")
            }

            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body("Login successful"))
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error"))
        }
    }

    @PostMapping("/forgot")
    @Async
    fun forgot(@RequestBody payload: LoginDto): CompletableFuture<ResponseEntity<String>> {
        val user = userService.findByName(payload.mail) ?: return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"))

        val code = userService.saveCode(payload.mail)

        if (code != null) {
            CompletableFuture.runAsync {
                emailService.sendHtmlEmail(user.mail, "Forgot password", "<p>Your code for change password: <b>$code</b></p>")
            }

            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body("Login successful"))
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error"))
        }
    }

    @PostMapping("/new-password")
    fun newPassword(@RequestBody payload: LoginDto): ResponseEntity<LoginResponseDto?>{

        val user = userService.findByName(payload.mail) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            LoginResponseDto(
                token = null
            )
        )

        if (payload.code != null && user.code != null) {
            if (!hashService.checkBcrypt(payload.code, user.code)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    LoginResponseDto(
                        token = null
                    )
                )
            }
        }else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                LoginResponseDto(
                    token = null
                )
            )

        if (payload.password != null){
            val _user = UserDto(
                mail = payload.mail,
                password = hashService.hashBcrypt(payload.password),
            )
            if(!userService.newPassword(_user))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    LoginResponseDto(
                        token = null
                    )
                )
            val userWithNewPassword = userService.findByName(payload.mail) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                LoginResponseDto(
                    token = null
                )
            )
            return ResponseEntity.status(HttpStatus.OK).body(
                LoginResponseDto(
                    token = tokenService.createToken(userWithNewPassword)
                )
            )
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                LoginResponseDto(
                    token = null
                )
            )
        }
    }

    @PostMapping("/code-accept")
    fun codeAccept(@RequestBody payload: LoginDto): ResponseEntity<LoginResponseDto?>{

        val user = userService.findByName(payload.mail) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            LoginResponseDto(
                token = null
            )
        )

        if (payload.code != null && user.code != null) {
            if (!hashService.checkBcrypt(payload.code, user.code)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    LoginResponseDto(
                        token = null
                    )
                )
            }
        }else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                LoginResponseDto(
                    token = null
                )
            )

        if(!userService.clearCode(payload.mail))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                LoginResponseDto(
                    token = null
                )
            )

        return ResponseEntity.status(HttpStatus.OK).body(
            LoginResponseDto(
                token = tokenService.createToken(user)
            )
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (!userService.existsByName(payload.mail)) {
            throw ApiException(400, "Name already exists")
        }
        val user = UserDto(
            mail = payload.mail,
            password = hashService.hashBcrypt(payload.password),
        )
        val savedUser = userService.save(user)
        return LoginResponseDto(
            token = tokenService.createToken(savedUser),
        )
    }

}
