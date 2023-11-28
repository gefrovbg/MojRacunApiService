package endpoints

import com.google.gson.Gson
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import security.service.toUser

@RestController
@RequestMapping("/api/test")
class TestEndpoint {

    @GetMapping(value = ["/get"] ,produces = ["application/json"])
    fun getTest(){

    }

}