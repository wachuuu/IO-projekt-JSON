package pl.put.poznan.jsontools.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


@RestController
@RequestMapping("/json")
public class JSONToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);

    @GetMapping("/minify")
    public ResponseEntity<String> minifyJSON(@RequestBody Map<String, Object> json) {
        logger.debug("Minified: "+json.toString());

        return new ResponseEntity<>("Minified JSON", HttpStatus.OK);
    }

    @GetMapping("/beautify")
    public ResponseEntity<String> beautifyJSON(@RequestBody Map<String, Object> json) {
        logger.debug("Beautified: "+json.toString());

        return new ResponseEntity<>("Beautified JSON", HttpStatus.OK);
    }

    @GetMapping("/custom/beautify")
    public ResponseEntity<String> customBeautifyJSON(@RequestBody Map<String, Object> json,
        @RequestParam(value="transformations", defaultValue="tabs, newlines, spaces") String[] transformations) {

        logger.debug("Beautified: "+json.toString());
        logger.debug("Params"+Arrays.toString(transformations));

        return new ResponseEntity<>("Beautified JSON", HttpStatus.OK);
    }
}
