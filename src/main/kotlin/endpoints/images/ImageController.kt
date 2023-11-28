package endpoints.images

import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/images")
class ImageController {

    @RequestMapping(value = ["/get"], method = [RequestMethod.GET], produces = [MediaType.IMAGE_JPEG_VALUE])
    @Throws(
        IOException::class
    )
    fun getImage(response: HttpServletResponse,@RequestParam("fileName") fileName: String,) {
        val imgFile = ClassPathResource("images/$fileName")
        response.contentType = MediaType.IMAGE_JPEG_VALUE
        StreamUtils.copy(imgFile.getInputStream(), response.outputStream)
    }

}